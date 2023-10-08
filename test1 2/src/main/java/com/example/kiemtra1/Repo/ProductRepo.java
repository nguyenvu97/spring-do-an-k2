package com.example.kiemtra1.Repo;

import com.example.kiemtra1.Model.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    void deleteProductById(Long id);
    @Query("SELECT p FROM Product p WHERE p.productName LIKE %:keyword%")
    List<Product> searchProductsByKeyword(String keyword);
    Optional<Product> findByImg(String fileName);
    List<Product> findByCategory(String category);
    List<Product> findTop4ByOrderByIdDesc(PageRequest of);
}
