package kr.ac.jj.algo.service;

import javassist.NotFoundException;
import kr.ac.jj.algo.domain.Likes;
import kr.ac.jj.algo.domain.Post;
import kr.ac.jj.algo.domain.Reply;
import kr.ac.jj.algo.domain.User;
import kr.ac.jj.algo.dto.LikesDTO;
import kr.ac.jj.algo.dto.PostDTO;
import kr.ac.jj.algo.dto.ReplyDTO;
import kr.ac.jj.algo.exception.ApiException;
import kr.ac.jj.algo.exception.ErrorCode;
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

    public PostDTO createPost(User user, PostDTO.Creation request) {
        Post post = modelMapper.map(request, Post.class);
        post.setUser(user);
        post.setCreatedAt(LocalDateTime.now());
        post = postRepository.save(post);
        return modelMapper.map(post, PostDTO.class);
    }

    public PostDTO.Detail patchPostById(User user, Integer id, PostDTO.Patch request) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ApiException(ErrorCode.POST_NOT_FOUND));
        if (!post.getUser().equals(user)) {
            throw new ApiException(ErrorCode.NO_PERMISSION);
        }
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post = updatePost(post);
        return modelMapper.map(post, PostDTO.Detail.class);
    }

    public void deletePostById(User user, Integer id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ApiException(ErrorCode.POST_NOT_FOUND));
        if (!post.getUser().equals(user)) {
            throw new ApiException(ErrorCode.NO_PERMISSION);
        }
        deletePost(post);
    }

    public Post updatePost(Post post) {
        post.setUpdatedAt(LocalDateTime.now());
        return postRepository.save(post);
    }

    public void deletePost(Post post) {
        postRepository.delete(post);
    }

    public PostDTO.Detail loadPostById(int id) {
        var post = postRepository.findById(id).orElseThrow(() -> new ApiException(ErrorCode.POST_NOT_FOUND));
        var result = modelMapper.map(post, PostDTO.Detail.class);
        result.setNumLikes(loadLikesCountByPostId(result.getId()));
        return result;
    }

    public Page<Post> loadPostsByUser(User user, Pageable pageable) {
        return postRepository.findByUser(user, pageable);
    }

    public Page<PostDTO> loadPosts(Pageable pageable) {
        var result= postRepository.findAll(pageable)
                .map(p -> modelMapper.map(p, PostDTO.class));
        result.forEach(p -> p.setNumLikes(loadLikesCountByPostId(p.getId())));
        return result;
    }

    public Page<ReplyDTO> loadRepliesByPostId(Integer id, Pageable pageable) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ApiException(ErrorCode.POST_NOT_FOUND));
        return replyRepository.findByPost(post, pageable).map(r -> modelMapper.map(r, ReplyDTO.class));
    }

    public LikesDTO likePostById(Integer id, User user) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ApiException(ErrorCode.POST_NOT_FOUND));
        Likes likes = likesRepository.findByUserAndPost(user, post).orElse(new Likes(user, post));
        likes = likesRepository.save(likes);
        return modelMapper.map(likes, LikesDTO.class);
    }

    public Long loadLikesCountByPostId(Integer id) {
        return likesRepository.countByPostId(id);
    }
}
