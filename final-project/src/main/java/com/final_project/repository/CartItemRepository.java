package com.final_project.repository;

import com.final_project.entity.Cart;
import com.final_project.entity.CartItem;
import com.final_project.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    // 📌 Tìm CartItem theo giỏ hàng và sản phẩm
    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);

    // 📌 Xóa CartItem theo Cart và Product
    void deleteByCartAndProduct(Cart cart, Product product);
}
