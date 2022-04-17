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
  public @ResponseBody ResponseEntity<AuthDTO.Response> authenticate(@RequestBody AuthDTO.Request request, HttpServletResponse httpServletResponse) {
    AuthDTO.Response response = authService.authenticate(request);
    return ResponseEntity.ok(response);
  }

  @GetMapping(value = "/reissue")
  public @ResponseBody ResponseEntity<AuthDTO.Response> reissue(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
    AuthDTO.Response response = authService.reissue(httpServletRequest, httpServletResponse);
    return ResponseEntity.ok(response);
  }


}