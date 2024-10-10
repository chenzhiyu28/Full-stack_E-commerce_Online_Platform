package com.leopold.store.service;

import com.leopold.store.entity.Product;
import com.leopold.store.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
public class ProductServiceTest {
    @Autowired
    private ProductServiceImpl productService;

    @Test
    public void testFind4TopProducts() {
        List<Product> products = productService.findHotProducts();

        assertEquals(products.size(), 4);
        assertTrue(products.getFirst().getPriority() >= products.getLast().getPriority());

        System.out.println(products);
    }
}
