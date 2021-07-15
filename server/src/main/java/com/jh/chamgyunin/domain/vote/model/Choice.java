package com.jh.chamgyunin.domain.vote.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "choices")
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Choice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "choice_id")
    private Long id;

    @Column(name = "num_user", nullable = false)
    private Long numUser = 0L;

    @Column(name = "name", nullable = false)
    private String name;


    public Choice(String name) {
        this.name = name;
    }

    public static Choice of(final String name) {
        return new Choice(name);
    }
}
