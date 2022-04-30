package kr.ac.jj.algo.controller.api.v1;

import kr.ac.jj.algo.domain.Authority;
import kr.ac.jj.algo.domain.User;
import kr.ac.jj.algo.dto.AdminsListDTO;
import kr.ac.jj.algo.dto.UserDTO;
import kr.ac.jj.algo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping(value = "/{username}")
    public UserDTO.Detail getUserByUsername(@PathVariable String username) {
        return userService.loadUserDetailByUsername(username);
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
