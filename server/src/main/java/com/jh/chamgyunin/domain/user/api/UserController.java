package com.jh.chamgyunin.domain.user.api;

import com.jh.chamgyunin.domain.auth.interceptor.IsUserLoggedIn;
import com.jh.chamgyunin.domain.user.dto.PointDto;
import com.jh.chamgyunin.domain.user.dto.SignUpRequest;
import com.jh.chamgyunin.domain.user.dto.SignUpResponse;
import com.jh.chamgyunin.domain.user.service.UserService;
import com.jh.chamgyunin.global.argumentresolver.LoginUserId;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Api(tags={"User"})
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "로컬 회원 가입")
    @PostMapping
    public ResponseEntity<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest dto){
        SignUpResponse signUpResponse = userService.insertUser(dto);
        return new ResponseEntity(signUpResponse, HttpStatus.CREATED);
    }

    @ApiOperation(value = "내 포인트 조회")
    @GetMapping("/point")
    @IsUserLoggedIn
    public ResponseEntity<PointDto> getMyPoint(@LoginUserId Long userId) {
        PointDto dto = PointDto.of(userService.getUserPoint(userId));
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
