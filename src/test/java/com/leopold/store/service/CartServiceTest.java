package com.leopold.store.service;

import com.leopold.store.entity.Cart;
import com.leopold.store.entity.DTO.CartDTO;
import com.leopold.store.entity.Product;
import com.leopold.store.entity.User;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
public class CartServiceTest {
    @Autowired
    ICartService cartService;
    @Autowired
    IUserService userService;
    @Autowired
    IProductService productService;

    @Test
    public void testAddToCart() {
        User user = userService.findUserById(13);
        Product product = productService.findProductByID(10000010);
        Product product2 = productService.findProductByID(10000011);

        Cart cart = Cart.builder().user(user).product(product).price(product.getPrice()).num(2).build();
        Cart cart2 = Cart.builder().user(user).product(product2).price(product2.getPrice()).num(2).build();

        cartService.addToCart(cart);

        Cart addedCart = cartService.findCartByUserAndProduct(user, product);
        assertNotNull(addedCart);
        assertEquals(addedCart.getNum(), 2);

        cartService.addToCart(cart);
        assertEquals(addedCart.getNum(), 4);
        assertEquals(cartService.findCartByUser(user).size(), 1);

        cartService.addToCart(cart2);
        assertEquals(cartService.findCartByUser(user).size(), 2);
    }

    @Test
    public void testDisplayCartDTOs() {
        List<CartDTO> cartDTOS = cartService.displayCartDTO(13);

        System.out.println(cartDTOS);
        assertEquals(cartDTOS.size(), 3);
        assertEquals(cartDTOS.getFirst().getCartNum(), 2);
    }

    @Test
    public void testGetCartDTOsByCIDs() {
        List<CartDTO> cartDTOS = new ArrayList<>();
        List<Integer> CIDs = new ArrayList<>();
        CIDs.add(5);
        CIDs.add(6);
        CIDs.add(7);

        cartDTOS = cartService.findCartDTOsByCIDs(CIDs, 13);
        assertEquals(cartDTOS.size(), 3);
        System.out.println(cartDTOS);
    }
}
