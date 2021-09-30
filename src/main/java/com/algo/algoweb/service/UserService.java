package com.algo.algoweb.service;

import java.util.NoSuchElementException;
import java.util.Optional;

import com.algo.algoweb.domain.User;
import com.algo.algoweb.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
  @Autowired
  private UserRepository userRepository;

  @Override
  public User loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findByUsername(username);
    if (user.isPresent()) {
      return userRepository.findByUsername(username).get();
    } else {
      throw new UsernameNotFoundException("User not found with username: " + username);
    }
  }
  
}
