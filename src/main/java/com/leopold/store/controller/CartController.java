package com.leopold.store.controller;

import com.leopold.store.entity.Cart;
import com.leopold.store.entity.DTO.CartDTO;
import com.leopold.store.service.ICartService;
import com.leopold.store.service.IProductService;
import com.leopold.store.service.IUserService;
import com.leopold.store.util.JsonResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/cart")
public class CartController extends BaseController {
    private final ICartService cartService;
    private final IUserService userService;
    private final IProductService productService;

    public CartController(ICartService cartService, IUserService userService, IProductService productService) {
        this.cartService = cartService;
        this.userService = userService;
        this.productService = productService;
    }

    @PostMapping({"", "/"})
    public JsonResponse<Void> addCart(@RequestBody CartDTO cartDTO, HttpSession session) {
        Cart cart = cartDTO.generateCart();

        cart.setUser(userService.findUserById(getUIDFromSession(session)));
        cart.setProduct(productService.findProductByID(cartDTO.getProductID()));
        cart.setPrice(cart.getProduct().getPrice());

        cartService.addToCart(cart);

        return successResponse(null);
    }

    @GetMapping({"", "/"})
    public JsonResponse<List<CartDTO>> getCartItems(
            @RequestParam(name = "cids", required = false) List<Integer> ids,
            HttpSession session) {
        System.out.println(ids);

        if (ids == null || ids.isEmpty()) {
            return successResponse(cartService.displayCartDTO(getUIDFromSession(session)));
        }
        else {
            return successResponse(cartService.findCartDTOsByCIDs(ids, getUIDFromSession(session)));
        }
    }

    @PostMapping({"{cartID}/{increment}"})
    public JsonResponse<Integer> updateCart(@PathVariable Integer cartID,
                                         @PathVariable Integer increment,
                                         HttpSession session) {
        String username = getUsernameFromSession(session);
        cartService.updateCartNum(cartID, increment, username);
        return successResponse(cartService.findCartById(cartID).getNum());
    }
}


















