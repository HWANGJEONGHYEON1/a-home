package com.ably.demo.draw.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DrawerCreateDto {

    @NotBlank(message = "서랍이름을 입력해주세요.")
    @Schema(example = "신발")
    private String drawerName;
    @NotBlank(message = "회원 번호를 입력해주세요.")
    @Schema(example = "1")
    private Long userId;
}
