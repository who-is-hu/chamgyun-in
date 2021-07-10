package com.jh.chamgyunin.domain.tag.api;

import com.jh.chamgyunin.domain.auth.interceptor.IsUserLoggedIn;
import com.jh.chamgyunin.domain.tag.dto.UpdateInterestTagRequest;
import com.jh.chamgyunin.domain.tag.model.Tag;
import com.jh.chamgyunin.domain.tag.service.TagService;
import com.jh.chamgyunin.global.argumentresolver.LoginUserId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @IsUserLoggedIn
    @PatchMapping("/interest")
    public ResponseEntity<List<Tag>> updateMyInterestTags(@LoginUserId Long userId, @Valid @RequestBody UpdateInterestTagRequest dto) {
        List<Tag> updatedInterest = tagService.setMyInterestTag(userId, dto.toTags());
        return new ResponseEntity<>(updatedInterest, HttpStatus.OK);
    }

    @IsUserLoggedIn
    @GetMapping("/interest")
    public ResponseEntity<List<Tag>> getMyInterestTags(@LoginUserId Long userId) {
        return new ResponseEntity<>(tagService.getMyInterestTag(userId), HttpStatus.OK);
    }
}
