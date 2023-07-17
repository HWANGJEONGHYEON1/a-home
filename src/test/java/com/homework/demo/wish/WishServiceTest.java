package com.homework.demo.wish;

import com.homework.demo.draw.Drawer;
import com.homework.demo.draw.DrawerRepository;
import com.homework.demo.exception.BusinessException;
import com.homework.demo.product.Product;
import com.homework.demo.product.ProductDto;
import com.homework.demo.product.ProductRepository;
import com.homework.demo.user.User;
import com.homework.demo.user.UserRepository;
import com.homework.demo.user.dto.UserRequestDto;
import com.homework.demo.wish.dto.WishRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class WishServiceTest {
    @Autowired WishRepository wishRepository;
    @Autowired ProductRepository productRepository;
    @Autowired UserRepository userRepository;
    @Autowired DrawerRepository drawerRepository;
    @Autowired WishService wishService;

    User savedUser;
    Product savedProduct;
    Product savedProduct2;
    Drawer savedDrawer;
    Wish savedWish;

    @BeforeEach
    void setUp() {
        // user Setting
        User aaa = User.createUser(new UserRequestDto("aaa@a-bly.com", "1234", "aaa"));
        savedUser = userRepository.save(aaa);

        Product product1 = Product.createProduct("a-product", "imageUrl.com", 10000);
        savedProduct = productRepository.save(product1);

        Product product2 = Product.createProduct("b-product", "imageUrl.com", 20000);
        savedProduct2 = productRepository.save(product2);

        Drawer drawer = Drawer.createDrawer("신발", savedUser);
        savedDrawer = drawerRepository.save(drawer);

        Wish wish = Wish.createWish(savedProduct, savedUser, savedDrawer);
        savedWish = wishRepository.save(wish);
    }

    @Test
    @DisplayName("찜 등록")
    void wish() {
        WishRequestDto wishRequestDto = new WishRequestDto(savedProduct2.getId(), savedUser.getId(), savedDrawer.getId());

        Long wishId = wishService.wish(wishRequestDto);

        Wish wish = wishRepository.findById(wishId).get();
        assertThat(wish.getDrawer()).isEqualTo(savedDrawer);
        assertThat(wish.getProduct()).isEqualTo(savedProduct2);
        assertThat(wish.getUser()).isEqualTo(savedUser);
    }

    @Test
    @DisplayName("찜 등록 예외 : 찜한 상품이 내 다른 찜 서랍에 있을경우")
    void wish_exception() {

        WishRequestDto wishRequestDto1 = new WishRequestDto(savedProduct.getId(), savedUser.getId(), savedDrawer.getId());

        assertThatThrownBy(() -> wishService.wish(wishRequestDto1))
                .isInstanceOf(BusinessException.class);
    }

    @Test
    @DisplayName("찜 삭제")
    void wish_delete() {
        wishService.release(savedWish.getId());
        assertThat(wishRepository.findById(savedWish.getId()).isPresent()).isFalse();
    }

    @Test
    @DisplayName("찜 상품 리스트 조회")
    void wish_productList() {
        List<ProductDto> wishesProducts = wishService.getWishesProduct(savedUser.getId(), savedDrawer.getId(), 0, 20);

        assertThat(wishesProducts.size()).isEqualTo(1);
    }
}