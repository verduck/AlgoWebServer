package kr.ac.jj.algo.repository;

import kr.ac.jj.algo.domain.Application;
import kr.ac.jj.algo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {
    Optional<Application> findByUser(User user);
}
