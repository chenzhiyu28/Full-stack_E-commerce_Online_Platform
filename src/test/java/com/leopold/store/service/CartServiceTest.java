package com.leopold.store.service;

import com.leopold.store.entity.Cart;
import com.leopold.store.entity.DTO.CartDTO;
import com.leopold.store.entity.Product;
import com.leopold.store.entity.User;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.relational.core.sql.In;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class CartServiceTest {
    @Autowired
    ICartService cartService;
    @Autowired
    IUserService userService;
    @Autowired
    IProductService productService;


    private final Integer testUserID = 13;
    private final Integer testProductID1 = 10000010;
    private final Integer testProductID2 = 10000011;


    @Test
    public void testAddToCart() {
        User user = userService.findUserById(testUserID);
        Product product = productService.findProductByID(testProductID1);
        Product product2 = productService.findProductByID(testProductID2);
        Integer initialAmount1 = cartService.findCartByUserAndProduct(user, product) == null?
                0: cartService.findCartByUserAndProduct(user, product).getNum();
        Integer initialAmount2 = cartService.findCartByUserAndProduct(user, product2) == null?
                0: cartService.findCartByUserAndProduct(user, product2).getNum();

        Cart cart = Cart.builder().user(user).product(product).price(product.getPrice()).num(2).build();
        Cart cart2 = Cart.builder().user(user).product(product2).price(product2.getPrice()).num(2).build();


        cartService.addToCart(cart);
        Cart addedCart = cartService.findCartByUserAndProduct(user, product);
        assertNotNull(addedCart);
        assertEquals(addedCart.getNum(), initialAmount1 + 2);

        cartService.addToCart(cart);
        addedCart = cartService.findCartByUserAndProduct(user, product);
        assertEquals(addedCart.getNum(), initialAmount1 + 4);
        assertNotNull(cartService.findCartByUser(user).size());

        cartService.addToCart(cart2);
        Cart addedCart2 = cartService.findCartByUserAndProduct(user, product2);
        assertEquals(addedCart2.getNum(), initialAmount2 + 2);
    }

    @Test
    public void testDisplayCartDTOs() {
        List<CartDTO> cartDTOS = cartService.displayCartDTO(testUserID);

        System.out.println(cartDTOS);
        assertEquals(cartDTOS.size(), 3);
        assertEquals(cartDTOS.getFirst().getCartNum(), 9);
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
