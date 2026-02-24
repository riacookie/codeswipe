package com.codeswipe.backend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "projects")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;

    private String title;

    @Column(columnDefinition = "TEXT") // Better for long descriptions
    private String description;

    private String difficultyLevel;
    private LocalDate createdDate;
    private LocalDate endDate;
    private String skillName;

    @ManyToOne
    @JoinColumn(name = "user_id") // The creator of the project
    private User user;

    @OneToMany(mappedBy = "project")
    @JsonIgnore // Prevents infinite loop during API calls
    private List<Swipe> swipes;
}