package com.jh.chamgyunin.domain.post.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jh.chamgyunin.domain.user.model.User;
import com.jh.chamgyunin.domain.vote.model.*;
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

    @Column(name="title", nullable = false)
    private String title;

    @Column(name="body", nullable = false)
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User owner;

    @Setter
    @Column(name = "tag_names")
    @JsonProperty(value = "tag_names")
    private String tags;

    @Setter
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "post_id", nullable = false)
    private List<Choice> choices = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private WorryState state = WorryState.IN_PROGRESS;

    @Enumerated(EnumType.STRING)
    @JsonProperty(value = "worry_type")
    @Column(name = "worry_type", nullable = false)
    private WorryType worryType;

    @JsonProperty(value = "vote_type")
    @Enumerated(EnumType.STRING)
    @Column(name = "vote_type", nullable = false)
    private VoteType voteType = VoteType.SELECT_ONE;

    @Column(name="created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Builder
    public Post(String title, String body, User owner, WorryType worryType) {
        this.title = title;
        this.body = body;
        this.owner = owner;
        this.worryType = worryType;
    }
}
