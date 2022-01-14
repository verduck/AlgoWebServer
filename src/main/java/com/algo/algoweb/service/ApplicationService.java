package com.algo.algoweb.service;

import com.algo.algoweb.domain.Application;
import com.algo.algoweb.domain.User;
import com.algo.algoweb.repository.ApplicationRepository;
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

    public Application loadApplicationByUser(User user) {
        Optional<Application> optionalApplication = applicationRepository.findByUser(user);
        if (optionalApplication.isPresent()) {
            return optionalApplication.get();
        } else {
            Application application = new Application(user, "");
            application.setUser(user);
            return createApplication(application);
        }
    }

    public Application updateApplication(Application application) {
        return applicationRepository.save(application);
    }
}
