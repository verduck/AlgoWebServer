 package kr.ac.jj.algo.repository;

 import kr.ac.jj.algo.domain.Likes;
 import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;

 @Repository
 public interface LikesRepository extends JpaRepository<Likes, Integer> {
     long countByPostId(Integer postId);
 }
