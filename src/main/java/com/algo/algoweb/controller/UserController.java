package com.algo.algoweb.controller;

import com.algo.algoweb.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/v1/user")
public class UserController {
  @Autowired
  private UserService userService;
  
  @RequestMapping(value = "/me", method = RequestMethod.GET)
  public @ResponseBody String getMe() {
    return "Me";
  }
}
