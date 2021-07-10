package com.jh.chamgyunin.domain.tag.api;

import com.jh.chamgyunin.domain.tag.model.Tag;
import com.jh.chamgyunin.domain.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tag")
public class TagController {

    private final TagService tagService;

    @GetMapping
    public ResponseEntity<Page<Tag>> searchTags(@PageableDefault(size = 10, page = 0) Pageable pageable, @RequestParam(name = "key", defaultValue = "") String key) {
        Page<Tag> tags = tagService.findAllByNameStartingWith(key, pageable);
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }
}
