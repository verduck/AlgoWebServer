package com.algo.algoweb.controller;

import java.util.HashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.algo.algoweb.domain.User;
import com.algo.algoweb.dto.Dataset.Col;
import com.algo.algoweb.dto.Dataset.Dataset;
import com.algo.algoweb.dto.Dataset.Row;
import com.algo.algoweb.dto.Dataset.XMain;
import com.algo.algoweb.security.JwtService;
import com.algo.algoweb.service.AuthService;
import com.algo.algoweb.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/auth")
public class AuthController {
  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private AuthService authService;
  
  @Autowired
  private UserService userService;

  @Autowired
  private JwtService jwtService;

  @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
  public @ResponseBody ResponseEntity<HashMap<String, Object>> authenticate(HttpServletResponse httpServletResponse, @RequestBody HashMap<String, Object> request) {
    HashMap<String, Object> response = new HashMap<>();
    String username = (String) request.get("username");
    String password = (String) request.get("password");

    XMain result = authService.loginJJInstar(username, password);
    Dataset dataset = result.findDatasetById("ds_info");
    if (dataset == null) {
      response.put("result", false);
      response.put("message", "학번 또는 비밀번호가 잘못되었습니다.");
      return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
		try {
      authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(username, username)
      );
    } catch (BadCredentialsException e) {
      Row row = dataset.getRows().get(0);
      for (Col c : row.getCols()) {
        response.put(c.getId().toLowerCase(), c.getValue());
      }
      response.put("result", false);
      response.put("message", "등록되지 않은 사용자입니다.\n알고리즘 랩장에게 문의하세요.");
      return new ResponseEntity<>(response, HttpStatus.OK);
    }
    final User user = userService.loadUserByUsername(username);
    String token = jwtService.generateToken(user);
    String refreshToken = jwtService.generateRefreshToken(user);
    response.put("result", true);
    response.put("username", user.getUsername());
    response.put("name", user.getName());
    response.put("token", token);
    Cookie cookie = new Cookie("token", refreshToken);
    cookie.setMaxAge(60 * 60 * 24 * 7);
    cookie.setSecure(true);
    cookie.setHttpOnly(true);
    httpServletResponse.addCookie(cookie);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}