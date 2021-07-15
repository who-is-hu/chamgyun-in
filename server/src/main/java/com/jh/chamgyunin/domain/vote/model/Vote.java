package com.jh.chamgyunin.domain.vote.model;

import com.jh.chamgyunin.domain.user.model.User;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "vote")
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vote_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "choice_id")
    private Choice choiceId;

    @ManyToOne
    @JoinColumn(name = "worry_id")
    private Worry worryId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime created_at;


}