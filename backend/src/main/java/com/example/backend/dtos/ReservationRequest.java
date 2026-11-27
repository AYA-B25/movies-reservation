package com.example.backend.dtos;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.* ;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ReservationRequest {
    @NotNull
    private Long showtimeId;
    @NotEmpty
    private List<@NotNull Long> seatIds ; }
