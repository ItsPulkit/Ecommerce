package com.project.ecommerce.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.ecommerce.entities.Cart;
import com.project.ecommerce.entities.User;

public interface CartRepository extends JpaRepository<Cart, String> {

    Optional<Cart> findByUser(User user);

}
