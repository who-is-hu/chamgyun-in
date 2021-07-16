package com.jh.chamgyunin.domain.vote.dao;

import com.jh.chamgyunin.domain.vote.model.Choice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface ChoiceRepository extends JpaRepository<Choice, Long> {
}
