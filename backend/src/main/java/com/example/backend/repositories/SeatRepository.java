package com.example.backend.repositories;

import com.example.backend.entities.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByIdInAndTheaterId(Collection<Long> ids, Long theaterId);

}