package com.algo.algoweb.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import com.algo.algoweb.domain.User;
import com.algo.algoweb.dto.AuthRequest;
import com.algo.algoweb.dto.AuthResponse;
import com.algo.algoweb.dto.Dataset.Dataset;
import com.algo.algoweb.dto.Dataset.XMain;
import com.algo.algoweb.security.JwtProvider;
import com.algo.algoweb.service.AuthService;
import com.algo.algoweb.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
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
  private JwtProvider jwtProvider;

  @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
  public @ResponseBody ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest authRequest) {
    AuthResponse response = new AuthResponse();

    XMain result = authService.loginJJInstar(authRequest);
    Dataset dataset = result.findDatasetById("ds_info");
    if (dataset == null) {
      response.setResult(false);
      response.setMessage("학번 또는 비밀번호가 잘못되었습니다.");
      return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
    response.setResult(true);
		try {
      authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getUsername())
      );
    } catch (BadCredentialsException e) {
      response.setMessage("등록되지 않은 사용자입니다.\n알고리즘 랩장에게 문의하세요.");
      return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
    final User user = userService.loadUserByUsername(authRequest.getUsername());
    String token = jwtProvider.generateToken(user);
    response.setToken(token);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}