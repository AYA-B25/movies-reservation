package com.example.backend.dtos;

import com.example.backend.entities.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class ReservationResponse {
    private Long id;
    private ReservationStatus status;
    private String movieTitle;
    private LocalDateTime startTime;
    private List<String> seats;
    private BigDecimal totalPrice;
    private LocalDateTime createdAt;
}