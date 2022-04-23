package kr.ac.jj.algo.controller.api.v1;

import kr.ac.jj.algo.domain.User;
import kr.ac.jj.algo.dto.ApplicationDTO;
import kr.ac.jj.algo.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ApplicationDTO getMyApplication(@AuthenticationPrincipal User user) {
        return applicationService.loadApplicationByUser(user);
    }

    @PatchMapping
    public ApplicationDTO patchMyApplication(@AuthenticationPrincipal User user, @RequestBody ApplicationDTO.Request request) {
        return applicationService.patchApplicationByUser(user, request);
    }
}
