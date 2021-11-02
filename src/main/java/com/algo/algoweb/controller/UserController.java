package com.algo.algoweb.controller;

import com.algo.algoweb.domain.User;
import com.algo.algoweb.dto.UserDTO;
import com.algo.algoweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/v1/user")
public class UserController {
  @Autowired
  private UserService userService;
  
  @RequestMapping(value = "/me", method = RequestMethod.GET)
  public @ResponseBody UserDTO getMe() {
    UserDTO response;
    User user = userService.loadUserById(1);
    response = userService.convertUserToUserDTO(user);
    return response;
  }
}
