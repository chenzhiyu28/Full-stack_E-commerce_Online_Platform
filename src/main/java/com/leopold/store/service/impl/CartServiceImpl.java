package com.leopold.store.service.impl;

import com.leopold.store.entity.Cart;
import com.leopold.store.entity.DTO.CartDTO;
import com.leopold.store.entity.Product;
import com.leopold.store.entity.User;
import com.leopold.store.persistence.repository.CartRepository;
import com.leopold.store.persistence.repository.ProductRepository;
import com.leopold.store.persistence.repository.UserRepository;
import com.leopold.store.service.ICartService;
import com.leopold.store.service.ex.CartNotFoundException;
import com.leopold.store.service.ex.IllegalRequestException;
import com.leopold.store.service.ex.ProductNotFoundException;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements ICartService {
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    // 这个注解告诉Spring将EntityManager注入到我们的服务类中。 @PersistenceContext
    private final EntityManager entityManager;

    public CartServiceImpl(CartRepository cartRepository,
                           UserRepository userRepository,
                           EntityManager entityManager,
                           ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.entityManager = entityManager;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public Cart addToCart(Cart cart) {
        Date date = new Date();
        String userName = cart.getUser().getUsername();
        Cart existingCart = cartRepository.findCartByUserAndProduct(cart.getUser(), cart.getProduct());

        // 1. product in user's cart 2. product not in user's cart
        if (existingCart!= null) {
            existingCart.setNum(existingCart.getNum() + cart.getNum());
            existingCart.setPrice(cart.getPrice());
        } else {
            existingCart = cart;
        }
        existingCart.setModifiedTime(date);
        existingCart.setCreatedTime(date);
        existingCart.setCreatedUser(userName);
        existingCart.setModifiedUser(userName);

        return cartRepository.save(existingCart);
    }

    @Override
    @Transactional(readOnly = true)
    public Cart findCartById(Integer id) {
        return cartRepository.findCartById(id);
    }


    @Override
    @Transactional(readOnly = true)
    public List<Cart> findCartByUser(User user) {
        return cartRepository.findCartsByUserOrderByModifiedTimeDesc(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cart> findCartByUser(Integer userID) {
        return findCartByUser(userRepository.findUserById(userID));
    }

    @Override
    @Transactional
    public void updateCartNum(Integer id, Integer num, String userName) {
        /*
            Cart cart = findCartById(id);
            cart.setNum(num);
         */
        Cart cart = entityManager.find(Cart.class, id);
        if (cart == null) {
            throw new CartNotFoundException();
        }
        if (productRepository.findProductById(cart.getProduct().getId()) == null) {
            throw new ProductNotFoundException("The product in your cart is expired!");
        }
        cart.setNum(num);
        cart.setModifiedTime(new Date());
        cart.setModifiedUser(userName);
    }

    @Override
    @Transactional(readOnly = true)
    public Cart findCartByUserAndProduct(User user, Product product) {
        return cartRepository.findCartByUserAndProduct(user, product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CartDTO> displayCartDTO(User user) {
        List<Cart> carts = cartRepository.findCartsByUserOrderByModifiedTimeDesc(user);
        return transferToCartDTOs(carts);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CartDTO> displayCartDTO(Integer uid) {
        User user = userRepository.findUserById(uid);
        return displayCartDTO(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CartDTO> findCartDTOsByCIDs(List<Integer> CIDs, Integer uid) {
        List<CartDTO> selectedCarts = new ArrayList<>();

        for (Integer cid : CIDs) {
            Cart cart = findCartById(cid);
            if (!cart.getUser().getId().equals(uid)) {
                throw new IllegalRequestException();
            }
            selectedCarts.add(new CartDTO(cart));
        }

        return selectedCarts;
    }

    private List<CartDTO> transferToCartDTOs(List<Cart> carts) {
        return carts.stream()
                .map(cart ->
                    new CartDTO(cart.getId(),
                                cart.getUser().getId(),
                                cart.getProduct().getId(),
                                cart.getPrice(),
                                cart.getNum(),
                                cart.getProduct().getTitle(),
                                cart.getProduct().getImage(),
                                cart.getProduct().getPrice()))
                .collect(Collectors.toList());
    }
}
