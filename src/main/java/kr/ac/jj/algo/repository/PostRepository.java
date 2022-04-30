package kr.ac.jj.algo.repository;

import kr.ac.jj.algo.domain.Board;
import kr.ac.jj.algo.domain.Post;
import kr.ac.jj.algo.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
    Optional<Post> findById(Integer id);
    Page<Post> findByUser(User user, Pageable pageable);
    Page<Post> findAll(Pageable pageable);
    Page<Post> findByBoard(Board board, Pageable pageable);
}
