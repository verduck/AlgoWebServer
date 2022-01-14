package com.algo.algoweb.repository;

import java.util.List;
import java.util.Optional;

import com.algo.algoweb.domain.Authority;
import com.algo.algoweb.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
  Optional<User> findByUsername(String username);
  List<User> findByAuthority(Authority authority);
}
