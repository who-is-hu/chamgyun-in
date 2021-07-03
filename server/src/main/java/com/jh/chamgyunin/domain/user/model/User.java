package com.jh.chamgyunin.domain.user.model;

import com.jh.chamgyunin.domain.login.dto.UserProvider;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "users")
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "provider")
    private UserProvider provider;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime created_at;

    @Builder
    public User(String email, String nickname, UserProvider provider) {
        this.email = email;
        this.nickname = nickname;
        this.provider = provider;
    }
}
