package com.homework.demo.init.csv;


import com.homework.demo.product.Product;
import com.homework.demo.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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