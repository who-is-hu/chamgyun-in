package com.jh.chamgyunin.domain.user.service;

import com.jh.chamgyunin.domain.user.dao.UserRepository;
import com.jh.chamgyunin.domain.user.dto.SignUpRequest;
import com.jh.chamgyunin.domain.user.dto.SignUpResponse;
import com.jh.chamgyunin.domain.user.exception.UserAlreadyExistException;
import com.jh.chamgyunin.domain.user.exception.UserNotFoundException;
import com.jh.chamgyunin.domain.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public boolean isExistUser(final String email){
        return userRepository.findByEmail(email).isPresent();
    }

    public boolean isExistUser(final Long id){
        return userRepository.findById(id).isPresent();
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

    public Integer getUserPoint(final Long userId) {
        return findById(userId).getPoint();
    }

    public User findById(final Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(()->new UserNotFoundException(id));
    }

    public User findByEmail(final String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.orElseThrow(()->new UserNotFoundException(email));
    }
}
