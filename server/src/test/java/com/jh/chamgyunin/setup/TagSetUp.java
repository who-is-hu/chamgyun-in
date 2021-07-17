package com.jh.chamgyunin.setup;


import com.jh.chamgyunin.domain.tag.dao.TagRepository;
import com.jh.chamgyunin.domain.tag.model.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class TagSetUp {

    @Autowired
    private TagRepository tagRepository;

    public List<Tag> save() {
        List<Tag> tags = new ArrayList<>(Arrays.asList(
                Tag.of("life"),
                Tag.of("love"),
                Tag.of("food"),
                Tag.of("my1"),
                Tag.of("my2")));
        return tagRepository.saveAll(tags);
    }
}
