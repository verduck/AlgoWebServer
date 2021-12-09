package com.algo.algoweb.service;

import com.algo.algoweb.domain.Likes;
import com.algo.algoweb.domain.Post;
import com.algo.algoweb.domain.User;
import com.algo.algoweb.dto.LikesDTO;
import com.algo.algoweb.dto.PostDTO;
import com.algo.algoweb.dto.UserDTO;
import com.algo.algoweb.repository.LikesRepository;
import com.algo.algoweb.repository.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private LikesRepository likesRepository;

    @Autowired
    private ModelMapper modelMapper;

    public PostDTO writePost(PostDTO postDTO, User user) {
        postDTO.setCreatedAt(LocalDate.now());
        postDTO.setAuthor(modelMapper.map(user, UserDTO.class));
        return modelMapper.map(postRepository.save(modelMapper.map(postDTO, Post.class)), PostDTO.class);
    }

    public PostDTO editPost(PostDTO postDTO, User user) {
        Optional<Post> optionalPost = postRepository.findById(postDTO.getId());
        if (!optionalPost.isPresent()) {
            return null;
        }

        Post post = optionalPost.get();
        if (post.getUser().getId() != user.getId()) {
            return null;
        }

        post = postRepository.save(modelMapper.map(postDTO, Post.class));

        return modelMapper.map(post, PostDTO.class);
    }

    public PostDTO loadPostById(int id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            return modelMapper.map(optionalPost.get(), PostDTO.class);
        } else {
            return null;
        }
    }

    public Page<PostDTO> loadPostsByPage(Pageable pageable) {
        return postRepository.findAll(pageable).map(p -> modelMapper.map(p, PostDTO.class));
    }

    public LikesDTO likePost(PostDTO postDTO, User user) {
        Likes likes = new Likes();
        likes.setPost(modelMapper.map(postDTO, Post.class));
        likes.setUser(user);
        likes = likesRepository.save(likes);

        return modelMapper.map(likes, LikesDTO.class);
    }
}
