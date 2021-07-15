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

    @Column(name = "num_voter", nullable = false)
    private Long numVoter;

    @Column(name = "name", nullable = false)
    private String name;
}
