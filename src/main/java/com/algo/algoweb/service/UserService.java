package com.algo.algoweb.service;

import java.util.NoSuchElementException;

import com.algo.algoweb.domain.User;
import com.algo.algoweb.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
  @Autowired
  private UserRepository userRepository;

  @Override
  public User loadUserByUsername(String username) throws UsernameNotFoundException {
    try {
      return userRepository.findByUsername(username).get();
    } catch(NoSuchElementException e) {
      throw new UsernameNotFoundException("User not found with username: " + username);
    }
  }
  
}
