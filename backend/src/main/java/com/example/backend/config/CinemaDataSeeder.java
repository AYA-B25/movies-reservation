package com.example.backend.config;

import com.example.backend.entities.*;
import com.example.backend.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CinemaDataSeeder implements CommandLineRunner {

    // les quatre repositories, puis la méthode run :
    // c'est le contenu de ma réponse précédente


    private final MovieRepository movieRepository;
    private final TheaterRepository theaterRepository;
    private final SeatRepository seatRepository;
    private final ShowtimeRepository showtimeRepository;

    @Override
    public void run(String... args) {
        if (theaterRepository.count() > 0) {
            return;                                  // déjà rempli : on ne recommence pas
        }

        Movie movie = new Movie();
        movie.setTitle("Film de test");
        movie.setDescription("Un film pour tester la réservation");
        movie.setPosterImageUrl("https://exemple.com/affiche.jpg");
        movie.setDurationMinutes(120);
        movie = movieRepository.save(movie);

        Theater theater = new Theater();
        theater.setTitle("Salle 1");
        theater = theaterRepository.save(theater);

        List<Seat> seats = new ArrayList<>();
        for (char row = 'A'; row <= 'E'; row++) {
            for (int number = 1; number <= 8; number++) {
                Seat seat = new Seat();
                seat.setRowLabel(String.valueOf(row));
                seat.setNumber(number);
                seat.setTheater(theater);
                seats.add(seat);
            }
        }
        seatRepository.saveAll(seats);

        LocalDateTime start = LocalDateTime.now().plusDays(2)
                .withHour(20).withMinute(0).withSecond(0).withNano(0);
        Showtime showtime = new Showtime();
        showtime.setStartTime(start);
        showtime.setEndTime(start.plusMinutes(movie.getDurationMinutes()));
        showtime.setPrice(new BigDecimal("50.00"));
        showtime.setMovie(movie);
        showtime.setTheater(theater);
        showtimeRepository.save(showtime);
    }
}
