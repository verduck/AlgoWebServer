package com.algo.algoweb.controller;

import javax.servlet.http.HttpServletRequest;

import com.algo.algoweb.domain.User;
import com.algo.algoweb.dto.UserDTO;
import com.algo.algoweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
  @Autowired
  private UserService userService;
  
  @RequestMapping(value = "/me", method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<UserDTO> getMe(HttpServletRequest httpServletRequest) {
    UserDTO response = null;
    final String authorizationHeader = httpServletRequest.getHeader("Authorization");
    Integer userId = -1;
    String token = null;

    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      token = authorizationHeader.substring(7);
    }

    if (token != null) {
      response = userService.loadUserByToken(token);
    }

    if (response == null) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    } else {
      return new ResponseEntity<>(response, HttpStatus.OK);
    }
  }
}
