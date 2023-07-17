package com.homework.demo.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserResponseDto {
    private String email;
    private String name;

    public UserResponseDto(String email, String name) {
        this.email = email;
        this.name = name;
    }
}
