package kr.ac.jj.algo.service;

import kr.ac.jj.algo.domain.Post;
import kr.ac.jj.algo.domain.User;
import kr.ac.jj.algo.repository.LikesRepository;
import kr.ac.jj.algo.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;

    @Autowired
    public PostService(final PostRepository postRepository, final LikesRepository likesRepository) {
        this.postRepository = postRepository;
    }

    public Post createPost(Post post) {
        post.setCreatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    public Post updatePost(Post post) {
        post.setUpdatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    public void deletePost(Post post) {
        postRepository.delete(post);
    }

    public Post loadPostById(int id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            return optionalPost.get();
        } else {
            return null;
        }
    }

    public Page<Post> loadPostsByUser(User user, Pageable pageable) {
        return postRepository.findByUser(user, pageable);
    }

    public Page<Post> loadPostsByPage(Pageable pageable) {
        return postRepository.findAll(pageable);
    }
}
