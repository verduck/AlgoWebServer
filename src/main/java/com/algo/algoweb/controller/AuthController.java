package com.algo.algoweb.controller;

import java.util.HashMap;

import com.algo.algoweb.domain.User;
import com.algo.algoweb.dto.AuthRequest;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
  public @ResponseBody ResponseEntity<HashMap<String, Object>> authenticate(@RequestBody AuthRequest authRequest) {
    HashMap<String, Object> response = new HashMap<>();

    XMain result = authService.loginJJInstar(authRequest);
    Dataset dataset = result.findDatasetById("ds_info");
    if (dataset == null) {
      response.put("result", false);
      response.put("message", "학번 또는 비밀번호가 잘못되었습니다.");
      return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
    response.put("result", true);
		try {
      authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getUsername())
      );
    } catch (BadCredentialsException e) {
      Row row = dataset.getRows().get(0);
      for (Col c : row.getCols()) {
        response.put(c.getId().toLowerCase(), c.getValue());
      }
      response.put("message", "등록되지 않은 사용자입니다.\n알고리즘 랩장에게 문의하세요.");
      return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
    final User user = userService.loadUserByUsername(authRequest.getUsername());
    String token = jwtService.generateToken(user);
    response.put("token", token);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}