package com.ably.demo.user;


import com.ably.demo.common.ResponseObject;
import com.ably.demo.draw.DrawerService;
import com.ably.demo.user.dto.UserRequestDto;
import com.ably.demo.user.dto.UserResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "회원 저장 API", description = "회원 저장 합니다.", tags = { "UserController Controller" })
    @PostMapping("/user")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseObject<UserResponseDto> register(@RequestBody @Valid UserRequestDto userRequestDto) {
        return ResponseObject.toResponse(userService.registerUser(userRequestDto));
    }

    @Operation(summary = "회원 정보 조회 API", description = "회원 정보 조회를 합니다.", tags = { "UserController Controller" })
    @GetMapping("/users")
    public ResponseObject<UserResponseDto> userinfo(@RequestParam String email) {
        return ResponseObject.toResponse(userService.selectUser(email));
    }
}
