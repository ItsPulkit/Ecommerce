package com.project.ecommerce.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.ecommerce.entities.Order;
import com.project.ecommerce.entities.User;

public interface OrderRepository extends JpaRepository<Order, String> {

    List<Order> findByUser(User user);

}
