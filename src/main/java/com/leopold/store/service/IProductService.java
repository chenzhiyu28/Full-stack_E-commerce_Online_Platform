package com.leopold.store.service;

import com.leopold.store.entity.Product;

import java.util.List;

public interface IProductService {
    List<Product> findHotProducts();

    Product findProductByID(Integer id);
}
