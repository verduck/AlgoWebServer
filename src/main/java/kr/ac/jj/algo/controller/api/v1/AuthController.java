package kr.ac.jj.algo.controller.api.v1;

import kr.ac.jj.algo.dto.AuthDTO;
import kr.ac.jj.algo.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
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
  public @ResponseBody ResponseEntity<AuthDTO> authenticate(@RequestBody AuthDTO.Request request) {
    return ResponseEntity.ok(authService.authenticate(request));
  }

  @GetMapping(value = "/reissue")
  public @ResponseBody ResponseEntity<AuthDTO> reissue(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    return ResponseEntity.ok(authService.reissue(httpServletRequest, httpServletResponse));
  }


}