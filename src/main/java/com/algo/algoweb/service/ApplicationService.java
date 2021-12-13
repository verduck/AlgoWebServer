package com.algo.algoweb.service;

import com.algo.algoweb.domain.Application;
import com.algo.algoweb.domain.User;
import com.algo.algoweb.dto.ApplicationDTO;
import com.algo.algoweb.repository.ApplicationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApplicationService {
    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ApplicationDTO loadApplicationByUser(User user) {
        Optional<Application> optionalApplication = applicationRepository.findByUser(user);
        if (optionalApplication.isPresent()) {
            return modelMapper.map(optionalApplication.get(), ApplicationDTO.class);
        } else {
            Application application = new Application();
            application.setUser(user);
            application = applicationRepository.save(application);
            return modelMapper.map(application, ApplicationDTO.class);
        }
    }

    public ApplicationDTO updateApplication(ApplicationDTO applicationDTO) {
        Application application = modelMapper.map(applicationDTO, Application.class);

        application = applicationRepository.save(application);

        return modelMapper.map(application, ApplicationDTO.class);
    }
}
