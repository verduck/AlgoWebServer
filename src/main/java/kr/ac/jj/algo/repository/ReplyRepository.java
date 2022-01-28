package kr.ac.jj.algo.repository;

import kr.ac.jj.algo.domain.Post;
import kr.ac.jj.algo.domain.Reply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    Page<Reply> findByPost(Post post, Pageable pageable);
}
