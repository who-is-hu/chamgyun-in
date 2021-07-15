package com.jh.chamgyunin.domain.vote.model;

import com.jh.chamgyunin.domain.user.model.User;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "participation")
public class Participation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participation_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "choice_id")
    private Choice choiceId;

    @ManyToOne
    @JoinColumn(name = "vote_id")
    private Vote voteId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime created_at;


}
