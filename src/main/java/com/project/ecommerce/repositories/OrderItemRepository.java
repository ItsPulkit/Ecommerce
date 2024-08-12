package com.project.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.ecommerce.entities.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
