package com.algo.algoweb.controller;

import com.algo.algoweb.domain.Application;
import com.algo.algoweb.domain.User;
import com.algo.algoweb.dto.ApplicationDTO;
import com.algo.algoweb.service.ApplicationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/application")
public class ApplicationController {
    private final ModelMapper modelMapper;
    private final ApplicationService applicationService;

    @Autowired
    public ApplicationController(final ModelMapper modelMapper, final ApplicationService applicationService) {
        this.modelMapper = modelMapper;
        this.applicationService = applicationService;
    }

    @GetMapping
    public ResponseEntity<ApplicationDTO.Response> getMyApplication(@AuthenticationPrincipal User user) {
        ApplicationDTO.Response response = new ApplicationDTO.Response();
        Application application = applicationService.loadApplicationByUser(user);
        response.setSuccess(true);
        response.setMessage("지원서를 성공적으로 불러왔습니다.");
        response.setApplication(modelMapper.map(application, ApplicationDTO.class));
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<ApplicationDTO.Response> putMyApplication(@AuthenticationPrincipal User user, @RequestBody ApplicationDTO.Request request) {
        ApplicationDTO.Response response = new ApplicationDTO.Response();
        Application application = modelMapper.map(request, Application.class);
        application = applicationService.updateApplication(application);
        response.setSuccess(true);
        response.setMessage("지원서를 성공적으로 변경하였습니다.");
        response.setApplication(modelMapper.map(application, ApplicationDTO.class));
        return ResponseEntity.ok(response);
    }
}
