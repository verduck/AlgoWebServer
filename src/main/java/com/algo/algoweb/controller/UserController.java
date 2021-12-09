package com.algo.algoweb.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.algo.algoweb.domain.User;
import com.algo.algoweb.dto.UserDTO;
import com.algo.algoweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
  @Autowired
  private UserService userService;
  
  @RequestMapping(value = "/me", method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<UserDTO> getMe(HttpServletRequest httpServletRequest, @AuthenticationPrincipal User user) {
    return new ResponseEntity<>(userService.convertUserToUserDTO(user), HttpStatus.OK);
  }
}
