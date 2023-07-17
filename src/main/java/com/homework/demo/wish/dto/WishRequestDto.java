package com.homework.demo.wish.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class WishRequestDto {
    @NotBlank(message = "상품을 입력해주세요")
    @Schema(example = "1")
    private Long productId;
    @NotBlank(message = "회원을 입력해주세요.")
    @Schema(example = "1")
    private Long userId;
    @NotBlank(message = "서랍을 입력해주세요")
    @Schema(example = "1")
    private Long drawerId;

    public WishRequestDto(Long productId, Long userId, Long drawerId) {
        this.productId = productId;
        this.userId = userId;
        this.drawerId = drawerId;
    }
}
