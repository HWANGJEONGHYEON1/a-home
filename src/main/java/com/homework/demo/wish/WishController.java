package com.homework.demo.wish;

import com.homework.demo.common.ResponseObject;
import com.homework.demo.wish.dto.WishRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class WishController {

    private final WishService wishService;


    @Operation(summary = "찜하기 API", description = "사용자번호와 서랍번호를 가지고 찜 데이터를 저장합니다.", tags = { "WishController Controller" })
    @PostMapping("/wish")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseObject<String> wish(@RequestBody WishRequestDto wishRequestDto) {
        wishService.wish(wishRequestDto);
        return ResponseObject.toResponse("생성되었습니다.");
    }

    @Operation(summary = "찜해제 API", description = "서랍안에 있는 하나의 찜을 해제합니다.", tags = { "WishController Controller" })
    @DeleteMapping("/wish/{wishId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseObject<String> delete(@PathVariable @Schema(example = "1") Long wishId) {
        wishService.release(wishId);
        return ResponseObject.toResponse("삭제되었습니다.");
    }
}
