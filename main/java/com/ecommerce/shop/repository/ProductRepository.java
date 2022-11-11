package com.ecommerce.shop.repository;

import com.ecommerce.shop.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findById(long id);
    Product findByName(String name);
    List<Product> findAllByOrderByIdAsc();
    List<Product> findAllByCategoryId(long categoryId);
}
