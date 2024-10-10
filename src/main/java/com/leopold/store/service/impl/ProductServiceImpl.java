package com.leopold.store.service.impl;

import com.leopold.store.entity.Product;
import com.leopold.store.persistence.repository.ProductRepository;
import com.leopold.store.service.IProductService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ProductServiceImpl implements IProductService {
    private final ProductRepository productRepository;
    private final RedisTemplate<String, Product> redisTemplate;

    public ProductServiceImpl(ProductRepository productRepository, RedisTemplate<String, Product> redisTemplate) {
        this.productRepository = productRepository;
        this.redisTemplate = redisTemplate;
    }


    @Override
    @Transactional(readOnly = true)
    public List<Product> findHotProducts() {
        String key = "hot_products";
        List<Product> hotProducts = redisTemplate.opsForList().range(key, 0, -1);

        if (hotProducts == null || hotProducts.isEmpty()) {
            hotProducts = productRepository.findTop4ByOrderByPriorityDesc();
            redisTemplate.opsForList().rightPushAll(key, hotProducts);
            redisTemplate.expire(key, 1, TimeUnit.HOURS); // 设置1小时过期
        }

        return hotProducts;
    }

    @Override
    @Transactional(readOnly = true)
    public Product findProductByID(Integer id) {
        Product product = productRepository.findProductById(id);

        if (product == null) {
            throw new RuntimeException("Product not found");
        }

        return product;
    }
}
