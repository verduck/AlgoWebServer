 package kr.ac.jj.algo.repository;

 import kr.ac.jj.algo.domain.Likes;
 import kr.ac.jj.algo.domain.Post;
 import kr.ac.jj.algo.domain.User;
 import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;

 import java.util.Optional;

 @Repository
 public interface LikesRepository extends JpaRepository<Likes, Integer> {
     long countByPostId(Integer postId);
     Optional<Likes> findByUserAndPost(User user, Post post);
 }
