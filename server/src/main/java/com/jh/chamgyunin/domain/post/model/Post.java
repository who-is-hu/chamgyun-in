package com.jh.chamgyunin.domain.post.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jh.chamgyunin.domain.tag.model.Tag;
import com.jh.chamgyunin.domain.user.model.User;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "posts")
@NoArgsConstructor
@ToString
public class Post {

    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="title")
    private String title;

    @Column(name="body")
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User owner;

    @Setter
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id")
    private List<Tag> tag = new ArrayList<>();

    @Column(name="created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder
    public Post(String title, String body, User owner) {
        this.title = title;
        this.body = body;
        this.owner = owner;
    }
}
