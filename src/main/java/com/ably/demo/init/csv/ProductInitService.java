package com.ably.demo.init.csv;


import com.ably.demo.draw.DrawerRepository;
import com.ably.demo.product.Product;
import com.ably.demo.product.ProductRepository;
import com.ably.demo.user.User;
import com.ably.demo.user.UserRepository;
import com.ably.demo.user.dto.UserRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductInitService {
    private final ProductRepository productRepository;
    @Transactional
    public void insertData(List<Product> products) {
        productRepository.saveAll(products);
    }
}