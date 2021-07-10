package com.jh.chamgyunin.domain.tag.dao;

import com.jh.chamgyunin.domain.tag.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Page<Tag> findAllByNameStartingWith(String key, Pageable pageable);
    Optional<Tag> findByName(String name);
}
