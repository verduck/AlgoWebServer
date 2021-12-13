package com.algo.algoweb.controller;

import com.algo.algoweb.domain.User;
import com.algo.algoweb.dto.ApplicationDTO;
import com.algo.algoweb.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/application")
public class ApplicationController {
    @Autowired
    private ApplicationService applicationService;

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ApplicationDTO get(@AuthenticationPrincipal User user) {
        return applicationService.loadApplicationByUser(user);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PATCH)
    public ApplicationDTO update(@RequestBody ApplicationDTO request, @AuthenticationPrincipal User user) {
        return applicationService.updateApplication(request);
    }
}
