package com.leopold.store.service;

import com.leopold.store.entity.Cart;
import com.leopold.store.entity.DTO.CartDTO;
import com.leopold.store.entity.Product;
import com.leopold.store.entity.User;
import org.springframework.data.relational.core.sql.In;

import java.util.List;

public interface ICartService {
    Cart addToCart(Cart cart);

    Cart findCartById(Integer id);

    List<Cart> findCartByUser(User user);

    List<Cart> findCartByUser(Integer userID);

    void updateCartNum(Integer id, Integer num, String userName);

    Cart findCartByUserAndProduct(User user, Product product);

    List<CartDTO> displayCartDTO(User user);

    List<CartDTO> displayCartDTO(Integer uid);

    List<CartDTO> findCartDTOsByCIDs(List<Integer> CIDs, Integer uid);
}
