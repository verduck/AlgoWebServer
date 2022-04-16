package kr.ac.jj.algo.controller.api.v1;

import kr.ac.jj.algo.domain.Application;
import kr.ac.jj.algo.domain.User;
import kr.ac.jj.algo.dto.ApplicationDTO;
import kr.ac.jj.algo.service.ApplicationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PostMapping
    public ResponseEntity<ApplicationDTO.Response> postMyApplication(@AuthenticationPrincipal User user, @RequestBody ApplicationDTO.Request request) {
        ApplicationDTO.Response response = new ApplicationDTO.Response();
        if (request.getIntroduction() == null) {
            response.setSuccess(false);
            response.setMessage("간단 소개글을 입력하세요.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        Application application = applicationService.loadApplicationByUser(user);
        application.setIntroduction(request.getIntroduction());
        application = applicationService.updateApplication(application);
        response.setSuccess(true);
        response.setMessage("지원서를 성공적으로 변경하였습니다.");
        response.setApplication(modelMapper.map(application, ApplicationDTO.class));
        return ResponseEntity.ok(response);
    }
}
