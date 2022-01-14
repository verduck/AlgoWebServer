package com.algo.algoweb.controller;

import javax.servlet.http.HttpServletRequest;

import com.algo.algoweb.domain.Authority;
import com.algo.algoweb.domain.User;
import com.algo.algoweb.dto.UserDTO;
import com.algo.algoweb.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final ModelMapper modelMapper;
    private final UserService userService;

    @Autowired
    public UserController(final ModelMapper modelMapper, final UserService userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<UserDTO.Response> getMe(@AuthenticationPrincipal User user) {
        UserDTO.Response response = new UserDTO.Response();
        response.setSuccess(true);
        response.setMessage("사용자 정보를 성공적으로 불러왔습니다.");
        response.setUser(modelMapper.map(user, UserDTO.class));
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<UserDTO.Response> putMe(@AuthenticationPrincipal User user, @RequestBody UserDTO.Request request) {
        UserDTO.Response response = new UserDTO.Response();
        User u = modelMapper.map(request, User.class);
        u.setId(user.getId());
        user = userService.updateUser(u);
        response.setSuccess(true);
        response.setMessage("사용자 정보를 성공적으로 변경하였습니다.");
        response.setUser(modelMapper.map(user, UserDTO.class));
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/admins")
    public ResponseEntity<List<UserDTO>> getAdmins() {
        List<UserDTO> response = userService.loadUsersByAuthority(Authority.ROLE_ADMIN).stream().map(u -> modelMapper.map(u, UserDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/applicants")
    public ResponseEntity<List<UserDTO>> getApplicants() {
        List<UserDTO> response = userService.loadUsersByAuthority(Authority.ROLE_APPLICANT).stream().map(u -> modelMapper.map(u, UserDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

}
