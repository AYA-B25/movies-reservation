package com.example.backend.repositories;

import com.example.backend.entities.ReservationSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface ReservationSeatRepository extends JpaRepository<ReservationSeat, Long> {

    boolean existsByShowtimeIdAndSeatIdIn(Long showtimeId, Collection<Long> seatIds);
}