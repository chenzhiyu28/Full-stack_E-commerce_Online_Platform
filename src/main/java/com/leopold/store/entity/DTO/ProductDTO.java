package com.leopold.store.entity.DTO;

import com.leopold.store.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Integer id;
    private String title;
    private Long price;
    private String image;

    public ProductDTO(Product product) {
        id = product.getId();
        title = product.getTitle();
        price = product.getPrice();
        image = product.getImage();
    }
}
