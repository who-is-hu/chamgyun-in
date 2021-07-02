package com.jh.chamgyunin.domain.user.api;

import com.jh.chamgyunin.domain.user.dto.signup.SignUpRequest;
import com.jh.chamgyunin.domain.user.dto.signup.SignUpResponse;
import com.jh.chamgyunin.domain.user.model.User;
import com.jh.chamgyunin.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<SignUpResponse> signUp(@Valid @RequestBody SignUpRequest dto){
        SignUpResponse signUpResponse = userService.insertUser(dto);
        return new ResponseEntity(signUpResponse, HttpStatus.CREATED);
    }

}
