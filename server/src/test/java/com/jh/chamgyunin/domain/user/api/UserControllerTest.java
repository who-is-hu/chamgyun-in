package com.jh.chamgyunin.domain.user.api;

import com.jh.chamgyunin.IntergrationTest;
import com.jh.chamgyunin.domain.login.dto.UserProvider;
import com.jh.chamgyunin.domain.user.dao.UserRepository;
import com.jh.chamgyunin.domain.user.dto.SignUpRequest;
import com.jh.chamgyunin.domain.user.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class UserControllerTest extends IntergrationTest {

    @Autowired
    private UserSetUp userSetUp;

    public ResultActions requestSignUp(SignUpRequest dto) throws Exception {
        return mvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andDo(print());
    }

    @Test
    public void 회원가입_성공() throws Exception {
        //given
        final User user = User.builder()
                .id(1L)
                .email("test@test.com")
                .provider(UserProvider.KAKAO)
                .nickname("testnick")
                .build();

        System.out.println(user);

        final SignUpRequest dto = SignUpRequest.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .provider(user.getProvider())
                .password("testpw1234")
                .build();

        //when
        final ResultActions resultActions = requestSignUp(dto);

        //then
        resultActions
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.email").value(user.getEmail()))
                .andExpect(jsonPath("$.nickname").value(user.getNickname()))
                .andExpect(jsonPath("$.provider").value(user.getProvider()))
        ;
    }

    @Test
    public void 회원가입_중복유저_실패() throws Exception {
        //given
        User user = User.builder()
                .email("dup@test.com")
                .nickname("test")
                .provider(UserProvider.KAKAO)
                .build();
        User existUser = userSetUp.saveUser(user);

        final SignUpRequest dto = SignUpRequest.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .provider(user.getProvider())
                .password("testpassword")
                .build();

        //when
        final ResultActions resultActions = requestSignUp(dto);

        //then
        resultActions
                .andExpect(status().is4xxClientError())
        ;
    }
}

@Component
class UserSetUp {
    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user){
        return userRepository.save(user);
    }
}