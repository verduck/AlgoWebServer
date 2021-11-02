package com.algo.algoweb.repository;

import com.algo.algoweb.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Integer> {
    long countByPostId(Integer postId);
}
