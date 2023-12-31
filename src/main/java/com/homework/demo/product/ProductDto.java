package com.homework.demo.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductDto {
    private String name;
    private String imageUrl;
    private int price;
}
