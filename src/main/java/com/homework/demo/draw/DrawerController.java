package com.homework.demo.draw;

import com.homework.demo.common.ResponseObject;
import com.homework.demo.draw.dto.DrawerCreateDto;
import com.homework.demo.draw.dto.DrawerResponseDetailDto;
import com.homework.demo.draw.dto.DrawerResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DrawerController {
    private final DrawerService drawerService;

    @Operation(summary = "찜 서랍 추가 API", description = "사용자 별 각 서랍 한 개 이상 추가할 수 있습니다.", tags = { "DrawerController Controller" })
    @PostMapping("/drawers")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseObject<String> createDrawer(@RequestBody DrawerCreateDto drawerCreateDto) {
        drawerService.createDrawer(drawerCreateDto);
        return ResponseObject.toResponse("생성되었습니다.");
    }

    @Operation(summary = "찜 서랍 조회 API", description = "사용자가 가지고 있는 서랍들을 조회합니다.", tags = { "DrawerController Controller" })
    @GetMapping("/drawers")
    public List<DrawerResponseDto> getDrawer(Long userId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
        return drawerService.getDrawers(userId, page, size);
    }

    @Operation(summary = "찜 서랍 삭제 API", description = "사용자가 가지고 있는 서랍을 삭제합니다.", tags = { "DrawerController Controller" })
    @DeleteMapping("/drawers/{drawerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseObject<String> release(@PathVariable("drawerId") @Schema(example = "1") Long drawerId, @RequestBody Long userId) {
        drawerService.deleteDrawer(drawerId, userId);
        return ResponseObject.toResponse("삭제되었습니다.");
    }

    @Operation(summary = "찜 서랍 디테일 조회 - 찜, 상품등", description = "하나의 서랍안에 찜한 여러개의 상품을 조회하고 찜의 수, 서랍이름을 명시합니다.", tags = { "DrawerController Controller" })
    @GetMapping("/drawer/{drawerId}")
    public DrawerResponseDetailDto drawerDetails(
            @PathVariable @Schema(example = "1") Long drawerId,
            @Schema(example = "1") Long userId,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "20") int size) {
        return drawerService.getDrawerDetail(userId, drawerId, page, size);
    }
}
