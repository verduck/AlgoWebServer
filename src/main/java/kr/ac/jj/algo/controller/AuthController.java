package kr.ac.jj.algo.controller;

import kr.ac.jj.algo.dto.AuthDTO;
import kr.ac.jj.algo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
  private final AuthService authService;

  @Autowired
  public AuthController(final AuthService authService) {
    this.authService = authService;
  }

  @PostMapping(value = "/authenticate")
  public @ResponseBody ResponseEntity<AuthDTO.Response> authenticate(@RequestBody AuthDTO.Request request, HttpServletResponse httpServletResponse) {
    AuthDTO.Response response = authService.authenticate(request);
    if (response.isSuccess()) {
      return ResponseEntity.ok(response);
    } else {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
    }
  }

  @RequestMapping(value = "/reissue", method = RequestMethod.GET)
  public @ResponseBody ResponseEntity<AuthDTO.Response> reissue(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    AuthDTO.Response response = authService.reissue(httpServletRequest, httpServletResponse);
    if (response.isSuccess()) {
      return ResponseEntity.ok(response);
    } else {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
  }


}