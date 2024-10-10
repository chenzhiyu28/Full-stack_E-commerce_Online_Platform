package com.leopold.store.controller;

import com.leopold.store.entity.DTO.ProductDTO;
import com.leopold.store.entity.Product;
import com.leopold.store.service.IProductService;
import com.leopold.store.util.JsonResponse;
import com.sun.net.httpserver.Authenticator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("api/products")
public class ProductController extends BaseController {
    private final IProductService productService;


    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    @GetMapping({"", "/"})
    public JsonResponse<List<ProductDTO>> findHotProducts() {
        List<Product> products = productService.findHotProducts();

        List<ProductDTO> productDTOS = products.stream().map(ProductDTO::new).toList();

        return successResponse(productDTOS);
    }

    @GetMapping("{id}")
    public JsonResponse<Product> findProduct(@PathVariable("id") Integer id) {
        System.out.println("Applying for product: "+id);
        return successResponse(productService.findProductByID(id));
    }


}
