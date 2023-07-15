package com.ably.demo.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequestDto {
    @NotBlank(message = "이메일을 입력해주세요.")
    @Schema(example = "hwang-ably@ably.com")
    private String email;
    @NotBlank(message = "패스워드를 입력해주세요.")
    @Schema(example = "ablyTest123!")
    private String password;

    public LoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
