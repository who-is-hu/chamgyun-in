package com.jh.chamgyunin.domain.tag.service;

import com.jh.chamgyunin.domain.tag.dao.TagRepository;
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

    /*
    * tags 중에서 현재 존재하지 않는 tag만 저장한다.
    * return : 저장된 tag
    * */
    public List<Tag> insertTag(List<Tag> tags) {
        ArrayList<Tag> savedTags = new ArrayList<>();
        for (Tag tag : tags) {
            Optional<Tag> existTag = tagRepository.findByName(tag.getName());
            if (!existTag.isPresent()) {
                savedTags.add(tagRepository.save(tag));
            }
        }
        return savedTags;
    }

    public Page<Tag> findAllByNameStartingWith(final String key, final Pageable pageable) {
        return tagRepository.findAllByNameStartingWith(key, pageable);
    }

    public List<Tag> getMyInterestTag(final Long userId) {
        User user = userService.findById(userId);
        return user.getInterestTags();
    }

    public List<Tag> setMyInterestTag(final Long userId, List<Tag> updatedTags) {
        User user = userService.findById(userId);
        user.setInterestTags(updatedTags);
        return user.getInterestTags();
    }
}
