package com.jh.chamgyunin.domain.user.model;

import com.jh.chamgyunin.domain.auth.dto.UserProvider;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
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
    public User(Long id, String email, String nickname, UserProvider provider) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.provider = provider;
    }
}
