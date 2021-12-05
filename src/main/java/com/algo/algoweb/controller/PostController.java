package com.algo.algoweb.controller;

import com.algo.algoweb.dto.PostDTO;
import com.algo.algoweb.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {
    @Autowired
    private PostService postService;

    public @ResponseBody PostDTO writePost(@RequestBody PostDTO request) {
        return postService.writePost(request);
    }

    public @ResponseBody PostDTO getById(@RequestParam int id) {
        return postService.getById(id);
    }

    public @ResponseBody Page<PostDTO> getPostsByPage(@RequestParam Pageable pageable) {
        return postService.getPostsByPage(pageable);
    }
}
