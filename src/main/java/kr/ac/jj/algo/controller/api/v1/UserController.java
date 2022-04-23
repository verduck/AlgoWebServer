package kr.ac.jj.algo.controller.api.v1;

import kr.ac.jj.algo.domain.Authority;
import kr.ac.jj.algo.domain.User;
import kr.ac.jj.algo.dto.AdminsListDTO;
import kr.ac.jj.algo.dto.UserDTO;
import kr.ac.jj.algo.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public UserDTO getMe(@AuthenticationPrincipal User user) {
        return userService.convertToUserDTO(user);
    }

    @GetMapping(value = "/admins")
    public AdminsListDTO getAdmins() {
        return userService.loadAdmins();
    }

    @GetMapping(value = "/applicants")
    public List<UserDTO> getApplicants() {
        return userService.loadUsersByAuthority(Authority.ROLE_APPLICANT);
    }

}
