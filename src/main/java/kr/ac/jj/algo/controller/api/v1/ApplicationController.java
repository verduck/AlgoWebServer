package kr.ac.jj.algo.controller.api.v1;

import kr.ac.jj.algo.domain.User;
import kr.ac.jj.algo.dto.ApplicationDTO;
import kr.ac.jj.algo.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/application")
public class ApplicationController {
    private final ApplicationService applicationService;

    @Autowired
    public ApplicationController(final ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping
    public ResponseEntity<ApplicationDTO.Response> getMyApplication(@AuthenticationPrincipal User user) {
        ApplicationDTO.Response response = applicationService.loadApplicationByUser(user);
        return ResponseEntity.ok(response);
    }

    @PatchMapping
    public ResponseEntity<ApplicationDTO.Response> patchMyApplication(@AuthenticationPrincipal User user, @RequestBody ApplicationDTO.Request request) {
        ApplicationDTO.Response response = applicationService.patchApplicationByUser(user, request);
        return ResponseEntity.ok(response);
    }
}
