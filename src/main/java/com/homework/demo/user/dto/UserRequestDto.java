package com.homework.demo.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    @NotBlank(message = "이메일은 필수로 입력해야합니다.")
    @Schema(example = "hwang-ably@ably.com")
    private String email;
    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    @Schema(example = "ablyTest123!")
    private String password;

    @NotBlank(message = "이름 필수 입력 값입니다.")
    @Schema(example = "ably")
    private String name;

    public void encryptPassword(String encodePassword) {
        this.password = encodePassword;
    }
}
