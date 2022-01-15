package kr.ac.jj.algo.repository;

import java.util.List;
import java.util.Optional;

import kr.ac.jj.algo.domain.Authority;
import kr.ac.jj.algo.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
  Optional<User> findByUsername(String username);
  List<User> findByAuthority(Authority authority);
}
