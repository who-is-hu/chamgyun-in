package com.jh.chamgyunin.domain.tag.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    @Column(name = "name", unique = true)
    private String name;

    private Tag(String name) {
        this.name = name;
    }

    public static Tag of(final String name) {
        return new Tag(name.toLowerCase());
    }

    public static List<Tag> of(final List<String> names) {
        return names.stream().map(name -> Tag.of(name.toLowerCase())).collect(Collectors.toList());
    }
}
