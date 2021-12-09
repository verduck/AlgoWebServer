package com.algo.algoweb.controller;

import com.algo.algoweb.dto.AuthRequestDTO;
import com.algo.algoweb.dto.AuthResponseDTO;
import com.algo.algoweb.dto.TokenDTO;
import com.algo.algoweb.dto.UserDTO;
import com.algo.algoweb.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
  @Autowired
  private AuthService authService;

  @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
  public @ResponseBody ResponseEntity<AuthResponseDTO> authenticate(@RequestBody AuthRequestDTO request, HttpServletResponse httpServletResponse) {
    AuthResponseDTO response = authService.authenticate(request);
    if (!response.isResult()) {
      return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    } else {
      Cookie cookie = new Cookie("token", response.getToken());
      cookie.setHttpOnly(true);
      httpServletResponse.addCookie(cookie);
      return new ResponseEntity<>(response, HttpStatus.OK);
    }
  }

  @RequestMapping(value = "/reissue", method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<TokenDTO> reissue(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    TokenDTO response = authService.reissue(httpServletRequest, httpServletResponse);
    if (response == null) {
      return new ResponseEntity<>( HttpStatus.UNAUTHORIZED);
    } else {
      return new ResponseEntity<>(response, HttpStatus.OK);
    }
  }


}