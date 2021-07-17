package com.jh.chamgyunin.domain.vote.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

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

    @JsonProperty(value = "num_user")
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

    public static List<Choice> of(final List<String> choiceNames) {
        return choiceNames.stream().map(name->Choice.of(name)).collect(Collectors.toList());
    }

    public void increaseNumUser() {
        this.numUser++;
    }
}
