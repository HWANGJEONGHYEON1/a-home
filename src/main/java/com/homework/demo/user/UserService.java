package com.homework.demo.user;


import com.homework.demo.config.jwt.JwtFilter;
import com.homework.demo.config.jwt.TokenProvider;
import com.homework.demo.exception.BusinessException;
import com.homework.demo.exception.ErrorCode;
import com.homework.demo.exception.RequestInvalidException;
import com.homework.demo.exception.UserNotFoundException;
import com.homework.demo.user.dto.LoginRequestDto;
import com.homework.demo.user.dto.UserRequestDto;
import com.homework.demo.user.dto.UserResponseDto;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public UserResponseDto registerUser(UserRequestDto userRequestDto) {
        User existUser = userRepository.findByEmail(userRequestDto.getEmail());

        if (Objects.nonNull(existUser)) {
            throw new RequestInvalidException(ErrorCode.REQUEST_INVALID_R008);
        }

        userRequestDto.encryptPassword(passwordEncoder.encode(userRequestDto.getPassword()));

        User user = User.createUser(userRequestDto);
        User savedUser = userRepository.save(user);
        return new UserResponseDto(savedUser.getEmail(), savedUser.getName(), null);
    }

    public UserResponseDto loginUser(LoginRequestDto requestDto, HttpServletResponse response) {
        var findUser = userRepository.findByEmail(requestDto.getEmail());

        if (Objects.isNull(findUser)) {
            throw new BusinessException(ErrorCode.REQUEST_INVALID_R009);
        }

        var usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(requestDto.getEmail(), requestDto.getPassword());
        Authentication authenticate = authenticationManagerBuilder.getObject().authenticate(usernamePasswordAuthenticationToken);
        String jwt = makeJwt(response, authenticate, findUser);
        UserResponseDto userResponseDto = new UserResponseDto(findUser.getEmail(), findUser.getName(), jwt);
        return userResponseDto;
    }

    private String makeJwt(HttpServletResponse response, Authentication authenticate, User findUser) {
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        String jwt = tokenProvider.createToken(authenticate);
        findUser.modifyLastLoginDateTime(LocalDateTime.now());
        response.setHeader(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return jwt;
    }

    @Transactional(readOnly = true)
    public UserResponseDto selectUser(String email) {
        User byEmail = userRepository.findByEmail(email);
        return new UserResponseDto(byEmail.getEmail(), byEmail.getName(), null);
    }

    @Transactional(readOnly = true)
    public User selectUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(ErrorCode.REQUEST_INVALID_R003));
    }
}
