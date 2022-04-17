package kr.ac.jj.algo.service;

import kr.ac.jj.algo.domain.Application;
import kr.ac.jj.algo.domain.User;
import kr.ac.jj.algo.dto.ApplicationDTO;
import kr.ac.jj.algo.repository.ApplicationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApplicationService {
    private final ModelMapper modelMapper;
    private final ApplicationRepository applicationRepository;

    @Autowired
    public ApplicationService(final ModelMapper modelMapper, final ApplicationRepository applicationRepository) {
        this.modelMapper = modelMapper;
        this.applicationRepository = applicationRepository;
    }

    public Application createApplication(Application application) {
        return applicationRepository.save(application);
    }

    public ApplicationDTO.Response loadApplicationByUser(User user) {
        ApplicationDTO.Response response = new ApplicationDTO.Response();
        Application application = applicationRepository.findByUser(user).orElse(new Application(user, ""));
        response.setMessage("지원서를 성공적으로 불러왔습니다.");
        response.setApplication(modelMapper.map(application, ApplicationDTO.class));
        return response;
    }

    public ApplicationDTO.Response patchApplicationByUser(User user, ApplicationDTO.Request request) {
        ApplicationDTO.Response response = new ApplicationDTO.Response();
        Application application = applicationRepository.findByUser(user).orElse(new Application(user, ""));
        application.setIntroduction(request.getIntroduction());
        application = applicationRepository.save(application);
        response.setMessage("지원서를 성공적으로 변경하였습니다.");
        response.setApplication(modelMapper.map(application, ApplicationDTO.class));
        return response;
    }
}
