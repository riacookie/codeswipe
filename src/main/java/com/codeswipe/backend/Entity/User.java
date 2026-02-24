package com.codeswipe.backend.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.List;

@Entity
@Table(name = "users")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String username;
    private String email;
    private String experienceLevel;
    private String role;

    @Column(name = "avt_url") // Matches your ERD
    private String avtUrl;

    private String telephone;

    // IMPORTANT: Use @JsonIgnore so you don't leak the whole DB when fetching a user
    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Project> projects;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Swipe> swipes;
}