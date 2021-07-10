package com.jh.chamgyunin.domain.tag.api;

import com.jh.chamgyunin.domain.auth.interceptor.IsUserLoggedIn;
import com.jh.chamgyunin.domain.tag.dto.UpdateInterestTagRequest;
import com.jh.chamgyunin.domain.tag.model.Tag;
import com.jh.chamgyunin.domain.tag.service.TagService;
import com.jh.chamgyunin.global.argumentresolver.LoginUserId;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tag")
@Api(tags = {"태그"})
public class TagController {

    private final TagService tagService;

    @ApiOperation(value = "태그 검색", notes = "tag?size={size}&page={page} 기본은 size=10, page=0")
    @GetMapping
    public ResponseEntity<Page<Tag>> searchTags(@ApiIgnore @PageableDefault(size = 10, page = 0) Pageable pageable,
                                                @ApiParam(value = "검색할 태그 이름 키값") @RequestParam(name = "key", defaultValue = "") String key) {
        Page<Tag> tags = tagService.findAllByNameStartingWith(key, pageable);
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @ApiOperation(value = "내 관심사 태그 갱신", notes = "받은 리스트로 덮어 씌우는 방식 초기화를 원하면 빈 배열을 전송")
    @IsUserLoggedIn
    @PatchMapping("/interest")
    public ResponseEntity<List<Tag>> updateMyInterestTags(@LoginUserId Long userId, @Valid @RequestBody UpdateInterestTagRequest dto) {
        List<Tag> updatedInterest = tagService.setMyInterestTag(userId, dto.toTags());
        return new ResponseEntity<>(updatedInterest, HttpStatus.OK);
    }

    @ApiOperation(value = "내 관심사 태그 조회")
    @IsUserLoggedIn
    @GetMapping("/interest")
    public ResponseEntity<List<Tag>> getMyInterestTags(@LoginUserId Long userId) {
        return new ResponseEntity<>(tagService.getMyInterestTag(userId), HttpStatus.OK);
    }
}
