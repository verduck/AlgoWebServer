package kr.ac.jj.algo.service;

import javassist.NotFoundException;
import kr.ac.jj.algo.domain.Likes;
import kr.ac.jj.algo.domain.Post;
import kr.ac.jj.algo.domain.Reply;
import kr.ac.jj.algo.domain.User;
import kr.ac.jj.algo.dto.PostDTO;
import kr.ac.jj.algo.repository.LikesRepository;
import kr.ac.jj.algo.repository.PostRepository;
import kr.ac.jj.algo.repository.ReplyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PostService {
    private final ModelMapper modelMapper;
    private final PostRepository postRepository;
    private final ReplyRepository replyRepository;
    private final LikesRepository likesRepository;

    @Autowired
    public PostService(ModelMapper modelMapper, final PostRepository postRepository, ReplyRepository replyRepository, final LikesRepository likesRepository) {
        this.modelMapper = modelMapper;
        this.postRepository = postRepository;
        this.replyRepository = replyRepository;
        this.likesRepository = likesRepository;
    }

    public PostDTO createPost(User user, PostDTO.Create request) {
        Post post = modelMapper.map(request, Post.class);
        post.setUser(user);
        post.setCreatedAt(LocalDateTime.now());
        post = postRepository.save(post);
        return modelMapper.map(post, PostDTO.class);
    }

    public Post updatePost(Post post) {
        post.setUpdatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    public void deletePost(Post post) {
        postRepository.delete(post);
    }

    public Post loadPostById(int id) throws NotFoundException {
        return postRepository.findById(id).orElseThrow(() -> new NotFoundException("해당 게시물을 찾을 수 없습니다. id : " + id));
    }

    public Page<Post> loadPostsByUser(User user, Pageable pageable) {
        return postRepository.findByUser(user, pageable);
    }

    public Page<Post> loadPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public Page<Reply> loadRepliesByPostId(Integer id, Pageable pageable) throws NotFoundException {
        Post post = postRepository.findById(id).orElseThrow(() -> new NotFoundException("해당 개시물을 찾을 수 없습니다. id: " + id));
        return replyRepository.findByPost(post, pageable);
    }

    public Likes likePostById(Integer id, User user) throws NotFoundException {
        Post post = postRepository.findById(id).orElseThrow(() -> new NotFoundException("해당 게시물을 찾을 수 없습니다. id: " + id));
        Optional<Likes> optionalLikes = likesRepository.findByUserAndPost(user, post);
        if (optionalLikes.isPresent()) {
            return optionalLikes.get();
        }
        Likes likes = new Likes(user, post);
        return likesRepository.save(likes);
    }

    public Long loadLikesCountByPostId(Integer id) {
        return likesRepository.countByPostId(id);
    }
}
