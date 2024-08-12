package com.project.ecommerce.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.ecommerce.entities.User;

public interface UserRepository extends JpaRepository<User, String> {
  Optional<User> findByEmail(String email);

  Optional<User> findByEmailAndPassword(String email, String password);

  List<User> findByNameContaining(String keywords);
}
