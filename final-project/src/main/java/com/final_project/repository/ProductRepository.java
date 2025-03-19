package com.final_project.repository;

import com.final_project.entity.Category;
import com.final_project.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    @Query("SELECT p.code FROM Product p WHERE p.category.id = :categoryId ORDER BY p.code DESC LIMIT 1")
    String findLatestProductCodeByCategoryId(@Param("categoryId") Long categoryId);

}
