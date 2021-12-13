package com.algo.algoweb.repository;

import com.algo.algoweb.domain.Application;
import com.algo.algoweb.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {
    public Optional<Application> findByUser(User user);
}
