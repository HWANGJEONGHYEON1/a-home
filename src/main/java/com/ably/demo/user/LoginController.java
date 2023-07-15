package com.ably.demo.user;

import com.ably.demo.common.ResponseObject;
import com.ably.demo.user.dto.LoginRequestDto;
import com.ably.demo.user.dto.UserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;

    @Operation(summary = "로그인 API", description = "로그인 성공 시 세션 저장 합니다.", tags = { "LoginController Controller" })
    @PostMapping("/login")
    public ResponseObject<UserResponseDto> login(@RequestBody @Valid LoginRequestDto requestDto, HttpServletRequest request, HttpServletResponse response) {
        return ResponseObject.toResponse(userService.loginUser(requestDto, request, response));
    }
}
