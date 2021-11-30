package com.algo.algoweb.service;

import com.algo.algoweb.domain.User;
import com.algo.algoweb.dto.UserDTO;
import com.algo.algoweb.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Override
  public User loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findByUsername(username);
    if (user.isPresent()) {
      return user.get();
    } else {
      throw new UsernameNotFoundException("User not found with username: " + username);
    }
  }

  public User loadUserById(Integer id) throws UsernameNotFoundException {
    Optional<User> optionalUser = userRepository.findById(id);

    if (optionalUser.isPresent()) {
      return optionalUser.get();
    } else {
      throw new UsernameNotFoundException("User not found with id: " + id);
    }
  }

  public User createUser(User user) {
    return userRepository.save(user);
  }

  public UserDTO convertUserToUserDTO(User user) {
    return modelMapper.map(user, UserDTO.class);
  }
  
}
