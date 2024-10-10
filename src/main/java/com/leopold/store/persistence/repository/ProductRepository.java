package com.leopold.store.persistence.repository;

import com.leopold.store.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
    List<Product> findTop4ByOrderByPriorityDesc();

    Product findProductById(Integer id);
}
