package com.algo.algoweb.service;

import com.algo.algoweb.domain.Post;
import com.algo.algoweb.dto.PostDTO;
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
    private ModelMapper modelMapper;

    public PostDTO writePost(PostDTO postDTO) {
        postDTO.setCreatedAt(LocalDate.now());
        return modelMapper.map(postRepository.save(modelMapper.map(postDTO, Post.class)), PostDTO.class);
    }

    public PostDTO getById(int id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            return modelMapper.map(optionalPost.get(), PostDTO.class);
        } else {
            return null;
        }
    }

    public Page<PostDTO> getPostsByPage(Pageable pageable) {
        return postRepository.findAll(pageable).map(p -> modelMapper.map(p, PostDTO.class));
    }
}
