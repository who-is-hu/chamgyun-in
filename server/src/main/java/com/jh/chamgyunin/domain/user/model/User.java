package com.jh.chamgyunin.domain.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jh.chamgyunin.global.model.UserProvider;
import com.jh.chamgyunin.domain.tag.model.Tag;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "email", nullable = false)
    private String email;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @Column(name = "nickname")
    private String nickname;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false)
    private UserProvider provider;

    @Setter
    @JoinColumn(name = "tag_id")
    @ManyToMany
    private List<Tag> interestTags = new ArrayList<>();

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
