package com.jh.chamgyunin.domain.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "provider")
    private String provider;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime created_at;

    @Builder
    public User(String email, String nickname, String provider) {
        this.email = email;
        this.nickname = nickname;
        this.provider = provider;
    }
}
