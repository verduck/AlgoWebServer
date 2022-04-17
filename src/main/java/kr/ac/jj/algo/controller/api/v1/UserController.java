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
    public ResponseEntity<AdminsListDTO> getAdmins() {
        AdminsListDTO adminsList = new AdminsListDTO();
        List<UserDTO> admins = userService.loadUsersByAuthority(Authority.ROLE_ADMIN).stream().map(u -> modelMapper.map(u, UserDTO.class)).collect(Collectors.toList());
        List<UserDTO> adminAssistants = userService.loadUsersByAuthority(Authority.ROLE_ADMIN_ASSISTANT).stream().map(u -> modelMapper.map(u, UserDTO.class)).collect(Collectors.toList());
        adminsList.setAdmins(admins);
        adminsList.setAdminAssistants(adminAssistants);
        return ResponseEntity.ok(adminsList);
    }

    @GetMapping(value = "/applicants")
    public ResponseEntity<List<UserDTO>> getApplicants() {
        List<UserDTO> response = userService.loadUsersByAuthority(Authority.ROLE_APPLICANT).stream().map(u -> modelMapper.map(u, UserDTO.class)).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

}
