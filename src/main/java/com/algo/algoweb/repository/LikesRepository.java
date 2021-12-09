 package com.algo.algoweb.repository;

 import com.algo.algoweb.domain.Likes;
 import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.stereotype.Repository;

 @Repository
 public interface LikesRepository extends JpaRepository<Likes, Integer> {
     long countByPostId(Integer postId);
 }
