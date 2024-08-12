package com.project.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.ecommerce.entities.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
}
