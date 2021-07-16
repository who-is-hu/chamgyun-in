package com.jh.chamgyunin.domain.vote.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "worry")
@Getter
@NoArgsConstructor
public class Worry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "worry_id")
    private Long id;

    @Setter
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    @JoinColumn(name = "worry_id", nullable = false)
    private List<Choice> choices = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private WorryState state = WorryState.IN_PROGRESS;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private WorryType type;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime created_at;

    private Worry(WorryType type) {
        this.type = type;
    }

    public static Worry of(WorryType worryType) {
       return new Worry(worryType);
    }

    public void addChoice(Choice choice) {
        this.choices.add(choice);
    }

    public void removeChoice(Choice choice) {
        this.choices.remove(choice);
    }
}
