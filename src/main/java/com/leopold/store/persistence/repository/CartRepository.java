package com.leopold.store.persistence.repository;

import com.leopold.store.entity.Cart;
import com.leopold.store.entity.Product;
import com.leopold.store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer>, JpaSpecificationExecutor<Cart> {
    Cart findCartById(Integer Id);
    List<Cart> findCartsByUserOrderByModifiedTimeDesc(User user);
    Cart findCartByProduct(Product product);
    List<Cart> findCartsByUserAndId(User user, Integer id);
    Cart findCartByUserAndProduct(User user, Product product);
}
