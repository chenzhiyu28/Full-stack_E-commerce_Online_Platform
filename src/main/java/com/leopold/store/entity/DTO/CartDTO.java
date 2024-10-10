package com.leopold.store.entity.DTO;

import com.leopold.store.entity.Cart;
import com.leopold.store.entity.Product;
import com.leopold.store.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {
    private Integer cartId;
    private Integer userId;
    private Integer productID;
    private Long cartPrice;
    private Integer cartNum;
    private String productTitle;
    private String productImage;
    private Long productPrice;

    public Cart generateCart() {
        return Cart.builder().price(cartPrice).num(cartNum).build();
    }

    public CartDTO(Cart cart) {
        this.cartId = cart.getId();
        this.userId = cart.getUser().getId();
        this.productID = cart.getProduct().getId();
        this.cartPrice = cart.getPrice();
        this.cartNum = cart.getNum();
        this.productTitle = cart.getProduct().getTitle();
        this.productImage = cart.getProduct().getImage();
        this.productPrice = cart.getProduct().getPrice();
    }
}
