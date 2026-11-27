package com.example.backend.controllers;

import com.example.backend.dtos.ReservationRequest;
import com.example.backend.dtos.ReservationResponse;
import com.example.backend.services.ReservationService;
import jakarta.validation.Valid;
import lombok.*;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.* ;


@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ReservationResponse> create( @Valid @RequestBody ReservationRequest request,  Authentication authentication) {

        ReservationResponse response =
                reservationService.createReservation(authentication.name(), request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}