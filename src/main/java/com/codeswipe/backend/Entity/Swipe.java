package com.codeswipe.backend.Entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "swipes")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Swipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long swipeId;

    @Column(length = 10) // LIKE or SKIP
    private String swipeType;

    private LocalDateTime swipeTime; // Changed to LocalDateTime for accuracy

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
}