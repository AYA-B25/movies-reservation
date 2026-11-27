package com.example.backend.entities;

import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "theaters")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Theater {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    private String posterImageUrl;

    @Column(nullable = false)
    private int durationMinutes;
    @OneToMany(mappedBy = "theater")
    private List<Seat> seats = new ArrayList<>();
}