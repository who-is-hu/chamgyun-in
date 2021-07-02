package com.jh.chamgyunin.domain.user.service;

import com.jh.chamgyunin.domain.user.dao.UserRepository;
import com.jh.chamgyunin.domain.user.dto.signup.SignUpRequest;
import com.jh.chamgyunin.domain.user.dto.signup.SignUpResponse;
import com.jh.chamgyunin.domain.user.exception.UserAlreadyExistException;
import com.jh.chamgyunin.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public boolean isExistUser(final String email){
        return userRepository.findByEmail(email).isPresent();
    }

    public SignUpResponse insertUser(final SignUpRequest dto){
        if(!isExistUser(dto.getEmail())){
            User user = userRepository.save(dto.toEntity());
            return SignUpResponse.builder()
                    .email(user.getEmail())
                    .nickname(user.getNickname())
                    .provider(user.getProvider())
                    .build();
        } else {
            throw new UserAlreadyExistException(dto.getEmail());
        }
    }
}
