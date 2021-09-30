package com.algo.algoweb.repository;

import java.util.Optional;

import com.algo.algoweb.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
  Optional<User> findByUsername(String username);
}
