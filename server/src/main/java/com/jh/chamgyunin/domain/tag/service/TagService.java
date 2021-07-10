package com.jh.chamgyunin.domain.tag.service;

import com.jh.chamgyunin.domain.tag.dao.TagRepository;
import com.jh.chamgyunin.domain.tag.dto.UpdateInterestTagRequest;
import com.jh.chamgyunin.domain.tag.model.Tag;
import com.jh.chamgyunin.domain.user.model.User;
import com.jh.chamgyunin.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
    private final UserService userService;

    public Tag insertTag(Tag tag) {
        Optional<Tag> existTag = tagRepository.findByName(tag.getName());
        if (!existTag.isPresent()) {
            return tagRepository.save(tag);
        }
        return existTag.get();
    }

    public Page<Tag> findAllByNameStartingWith(final String key, final Pageable pageable) {
        return tagRepository.findAllByNameStartingWith(key, pageable);
    }

    public List<Tag> getMyInterestTag(final Long userId) {
        User user = userService.findById(userId);
        return user.getInterestTags();
    }

    public List<Tag> setMyInterestTag(final Long userId, List<Tag> interestTags) {
        User user = userService.findById(userId);

        List<Tag> updatedInterest = new ArrayList<>();
        for (Tag tag : interestTags) {
            updatedInterest.add(insertTag(tag));
        }
        user.setInterestTags(updatedInterest);

        return user.getInterestTags();
    }
}
