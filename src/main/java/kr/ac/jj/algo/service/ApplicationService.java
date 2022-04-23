package kr.ac.jj.algo.service;

import kr.ac.jj.algo.domain.Application;
import kr.ac.jj.algo.domain.User;
import kr.ac.jj.algo.dto.ApplicationDTO;
import kr.ac.jj.algo.repository.ApplicationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public ApplicationDTO loadApplicationByUser(User user) {
        Application application = applicationRepository.findByUser(user).orElse(new Application(user, ""));
        return modelMapper.map(application, ApplicationDTO.class);
    }

    public ApplicationDTO patchApplicationByUser(User user, ApplicationDTO.Request request) {
        Application application = applicationRepository.findByUser(user).orElse(new Application(user, ""));
        application.setIntroduction(request.getIntroduction());
        application = applicationRepository.save(application);
        return modelMapper.map(application, ApplicationDTO.class);
    }
}
