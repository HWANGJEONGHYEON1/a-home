package com.homework.demo.product;

import com.homework.demo.common.BaseEntity;
import com.homework.demo.image.Image;
import io.micrometer.common.util.StringUtils;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter(AccessLevel.PRIVATE)
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private int price;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private Image image;

    public static Product createProduct(String name, String imageUrl, int price) {
        validate(name, imageUrl, price);

        Product product = new Product();
        product.setName(name);
        product.setImage(new Image(imageUrl));
        product.setPrice(price);
        return product;
    }

    private static void validate(String name, String imageUrl, int price) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("empty name");
        }
        if (price < 1) {
            throw new IllegalArgumentException("empty price");
        }
        if (StringUtils.isBlank(imageUrl)) {
            throw new IllegalArgumentException("empty imageUrl");
        }
    }
}