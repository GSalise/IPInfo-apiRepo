package com.georgesalise.apiRepo.api.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Column(name = "user_id")
    private Long userId;

    @Setter
    @Getter
    @Column(name = "email", nullable = false)
    private String email;

    @Setter
    @Getter
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Setter
    @Getter
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Setter
    @Getter
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserHistory> userHistory = new ArrayList<>();
}
