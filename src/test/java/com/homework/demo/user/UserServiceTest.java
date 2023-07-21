package com.homework.demo.user;

import com.homework.demo.exception.BusinessException;
import com.homework.demo.exception.RequestInvalidException;
import com.homework.demo.user.dto.LoginRequestDto;
import com.homework.demo.user.dto.UserRequestDto;
import com.homework.demo.user.dto.UserResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    MockHttpServletResponse response = mock(MockHttpServletResponse.class);
    MockHttpServletRequest request = mock(MockHttpServletRequest.class);


    @BeforeEach
    void setup() {
        UserRequestDto userRequestDto = getUserRequestDto("hhh@a-bly.com", "123456", "황정현");
        User user = User.createUser(userRequestDto);
        userRepository.saveAndFlush(user);
        MockHttpSession session = new MockHttpSession();

        request = new MockHttpServletRequest();
        request.setSession(session);
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

    }

    private static UserRequestDto getUserRequestDto(String email, String password, String name) {
        return new UserRequestDto(email, password, name);
    }

    @Test
    @DisplayName("회원 저장")
    void registerUser() {
        UserRequestDto 황블리 = getUserRequestDto("jjj@a-bly.com", "123123", "황블리");
        UserResponseDto user = userService.registerUser(황블리);

        User findUser = userRepository.findByEmail("jjj@a-bly.com");

        assertThat(findUser.getEmail()).isEqualTo("jjj@a-bly.com");
        assertThat(findUser.getName()).isEqualTo("황블리");
    }

    @Test
    @DisplayName("로그인 시 중복된 아이디 입력")
    void registerUser_exception() {
        assertThatThrownBy(() -> userService.registerUser(getUserRequestDto("hhh@a-bly.com", "123", "aaa")))
                .isInstanceOf(RequestInvalidException.class);
    }


    @Test
    @DisplayName("로그인 시 잘못된 아이디 입력")
    void login_exception() {
        assertThatThrownBy(() -> userService.loginUser(new LoginRequestDto("invalid_id@a-bly.com", "123"), response))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    @DisplayName("로그인 시 잘못된 패스워드 입력")
    void login_password_exception() {
        assertThatThrownBy(() -> userService.loginUser(new LoginRequestDto("hhh@a-bly.com", "12333"), response))
                .isInstanceOf(BadCredentialsException.class);
    }

    @Test
    @DisplayName("정상 로그인 처리")
    void loginUser() {
        UserResponseDto user = userService.loginUser(new LoginRequestDto("hhh@a-bly.com", "123456"), response);
        assertThat(user).isNotNull();
    }
}