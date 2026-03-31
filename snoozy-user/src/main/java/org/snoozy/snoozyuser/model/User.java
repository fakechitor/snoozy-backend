package org.snoozy.snoozyuser.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "google_sub", unique = true)
    private String googleSub;

    @Column(nullable = false)
    private String email;

    private String username;

    @Column(name = "avatar_id")
    private Long avatarId;

    @Column(nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;
}