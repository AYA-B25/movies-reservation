package com.example.backend.services ;
import com.example.backend.dtos.ReservationRequest;
import com.example.backend.dtos.ReservationResponse;
import com.example.backend.entities.*;
import com.example.backend.exceptions.*;
import com.example.backend.repositories.*;

import jakarta.transaction.Transactional;
import  lombok.* ;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final UserRepository userRepository;
    private final ShowtimeRepository showtimeRepository;
    private final SeatRepository seatRepository;
    private final ReservationSeatRepository reservationSeatRepository;
    private final ReservationRepository reservationRepository;

    @Transactional
    public ReservationResponse createReservation(String userEmail, ReservationRequest request) {

        // 1. L'utilisateur connecté
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UserNotFoundException("Utilisateur introuvable"));

        // 2. La séance
        Showtime showtime = showtimeRepository.findById(request.getShowtimeId())
                .orElseThrow(() -> new ShowtimeNotFoundException(
                        "Séance introuvable : " + request.getShowtimeId()));

        // 3. La séance doit être dans le futur
        if (!showtime.getStartTime().isAfter(LocalDateTime.now())) {
            throw new InvalidReservationException("La séance a déjà commencé");
        }

        // 4. Pas de siège demandé deux fois
        Set<Long> seatIds = new HashSet<>(request.getSeatIds());
        if (seatIds.size() != request.getSeatIds().size()) {
            throw new InvalidReservationException("Un siège est demandé plusieurs fois");
        }

        // 5. Tous les sièges existent et sont dans la salle de la séance
        List<Seat> seats = seatRepository.findByIdInAndTheaterId(
                seatIds, showtime.getTheater().getId());
        if (seats.size() != seatIds.size()) {
            throw new InvalidReservationException(
                    "Un ou plusieurs sièges n'existent pas dans cette salle");
        }

        // 6. Aucun siège déjà pris pour cette séance
        if (reservationSeatRepository.existsByShowtimeIdAndSeatIdIn(showtime.getId(), seatIds)) {
            throw new SeatAlreadyReservedException("Un ou plusieurs sièges sont déjà réservés");
        }

        // 7. Construire la réservation
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setShowtime(showtime);
        reservation.setStatus(ReservationStatus.CONFIRMED);
        reservation.setCreatedAt(LocalDateTime.now());
        reservation.setTotalPrice(
                showtime.getPrice().multiply(BigDecimal.valueOf(seats.size())));

        for (Seat seat : seats) {
            ReservationSeat reservationSeat = new ReservationSeat();
            reservationSeat.setSeat(seat);
            reservationSeat.setShowtime(showtime);
            reservationSeat.setPrice(showtime.getPrice());
            reservation.addSeat(reservationSeat);
        }

        // 8. Enregistrer, en protégeant contre deux réservations simultanées
        Reservation saved;
        try {
            saved = reservationRepository.saveAndFlush(reservation);
        } catch (DataIntegrityViolationException e) {
            throw new SeatAlreadyReservedException("Un ou plusieurs sièges viennent d'être réservés");
        }

        // 9. Convertir en DTO
        List<String> seatLabels = saved.getSeats().stream()
                .map(rs -> rs.getSeat().getRowLabel() + rs.getSeat().getNumber())
                .sorted()
                .toList();

        return new ReservationResponse(
                saved.getId(),
                saved.getStatus(),
                showtime.getMovie().getTitle(),
                showtime.getStartTime(),
                seatLabels,
                saved.getTotalPrice(),
                saved.getCreatedAt());
    }
}




