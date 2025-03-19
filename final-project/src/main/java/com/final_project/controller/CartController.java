package com.final_project.controller;

import com.final_project.dto.request.CartRequest;
import com.final_project.dto.response.ApiResponse;
import com.final_project.entity.Cart;
import com.final_project.service.CartService;
import com.final_project.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/cart")
public class CartController {
    private CartService cartService;
    private ProductService productService;

    @GetMapping("/{userId}")
    ApiResponse<Cart> getCart(@PathVariable("userId") String userId) {
        ApiResponse<Cart> response = new ApiResponse<>();
        response.setResult(cartService.getCartByUser(userId));
        return response;
    }

    @PostMapping("/{userId}/add")
    ApiResponse<Cart> addItemToCart(@PathVariable("userId") String userId, @RequestBody CartRequest request) {
        ApiResponse<Cart> response = new ApiResponse<>();
        response.setResult(cartService.addItemToCart(userId, request));
        response.setMessage("Add item successfully");
        return response;
    }

    @PutMapping("/{userId}/update")
    ApiResponse<Cart> updateItemInCart(@PathVariable("userId") String userId, @RequestBody CartRequest request) {
        ApiResponse<Cart> response = new ApiResponse<>();
        response.setResult(cartService.updateItemInCart(userId, request));
        response.setMessage("Update item successfully");
        return response;
    }

    @DeleteMapping("/{userId}/delete")
    ApiResponse<Cart> deleteItemInCart(@PathVariable("userId") String userId, @RequestBody CartRequest request) {
        ApiResponse<Cart> response = new ApiResponse<>();
        response.setResult(cartService.deleteItemInCart(userId, request));
        response.setMessage("Delete item successfully");
        return response;
    }
}
