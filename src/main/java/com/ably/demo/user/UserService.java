package com.ably.demo.user;


import com.ably.demo.config.SimplePasswordEncoder;
import com.ably.demo.exception.BusinessException;
import com.ably.demo.exception.ErrorCode;
import com.ably.demo.exception.RequestInvalidException;
import com.ably.demo.exception.UserNotFoundException;
import com.ably.demo.user.dto.LoginRequestDto;
import com.ably.demo.user.dto.UserRequestDto;
import com.ably.demo.user.dto.UserResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.ably.demo.common.CommonConstant.LOGIN_MEMBER;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class UserService {
    private final UserRepository userRepository;
    private final SimplePasswordEncoder passwordEncoder;

    public UserResponseDto registerUser(UserRequestDto userRequestDto) {
        User existUser = userRepository.findByEmail(userRequestDto.getEmail());

        if (Objects.nonNull(existUser)) {
            throw new RequestInvalidException(ErrorCode.REQUEST_INVALID_R008);
        }

        userRequestDto.encryptPassword(passwordEncoder.encode(userRequestDto.getPassword()));

        User user = User.createUser(userRequestDto);
        User savedUser = userRepository.save(user);
        return new UserResponseDto(savedUser.getEmail(), savedUser.getName());
    }

    public UserResponseDto loginUser(LoginRequestDto requestDto, HttpServletRequest request, HttpServletResponse response) {
        var findUser = userRepository.findByEmail(requestDto.getEmail());

        if (Objects.isNull(findUser)) {
            throw new BusinessException(ErrorCode.REQUEST_INVALID_R009);
        }

        if (passwordEncoder.matches(requestDto.getPassword(), findUser.getPassword())) {
            UserResponseDto userResponseDto = new UserResponseDto(findUser.getEmail(), findUser.getName());
            final HttpSession session = request.getSession();
            // 세션에 로그인 회원 정보를 보관
            session.setAttribute(LOGIN_MEMBER, findUser);
            findUser.modifyLastLoginDateTime(LocalDateTime.now());
            return userResponseDto;
        }

        throw new BusinessException(ErrorCode.REQUEST_INVALID_R009);
    }

    @Transactional(readOnly = true)
    public UserResponseDto selectUser(String email) {
        User byEmail = userRepository.findByEmail(email);
        return new UserResponseDto(byEmail.getEmail(), byEmail.getName());
    }

    @Transactional(readOnly = true)
    public User selectUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.REQUEST_INVALID_R003));
    }
}
