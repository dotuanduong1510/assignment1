package com.ecommerce.shop.service;

import com.ecommerce.shop.domain.Category;

import java.util.List;


public interface CategoryService {

    void save(Category category);
    List<Category> findAll();
}
