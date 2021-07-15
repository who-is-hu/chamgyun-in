package com.jh.chamgyunin.domain.vote.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jh.chamgyunin.domain.post.model.Post;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "worry")
@Getter
public class Worry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "worry_id")
    private Long id;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "choice_id", nullable = false)
    private List<Choice> choices = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private WorryState state;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private WorryType type;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime created_at;

    @Builder
    public Worry(WorryState state, WorryType type, Post post) {
        this.state = state;
        this.type = type;
        this.post = post;
    }

    public void addChoice(Choice choice) {
        this.choices.add(choice);
    }

    public void removeChoice(Choice choice) {
        this.choices.remove(choice);
    }
}
