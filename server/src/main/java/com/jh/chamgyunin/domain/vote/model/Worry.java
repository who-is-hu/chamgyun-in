package com.jh.chamgyunin.domain.vote.model;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
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
    @JoinColumn(name = "choice_id")
    private List<Choice> choices;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private WorryState state;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private WorryType type;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime created_at;
}
