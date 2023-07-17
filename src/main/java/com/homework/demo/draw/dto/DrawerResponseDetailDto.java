package com.homework.demo.draw.dto;

import com.homework.demo.product.ProductDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class DrawerResponseDetailDto {
    private String drawerName;
    private int wishCount;
    private List<ProductDto> productDto;

    @Builder(builderMethodName = "of")
    public DrawerResponseDetailDto(String drawerName, int wishCount, List<ProductDto> productDto) {
        this.drawerName = drawerName;
        this.wishCount = wishCount;
        this.productDto = productDto;
    }
}
