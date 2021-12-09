package com.algo.algoweb.controller;

import com.algo.algoweb.domain.User;
import com.algo.algoweb.dto.LikesDTO;
import com.algo.algoweb.dto.PostDTO;
import com.algo.algoweb.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {
    @Autowired
    private PostService postService;

    @RequestMapping(value = "/write", method = RequestMethod.POST)
    public @ResponseBody PostDTO writePost(@RequestBody PostDTO request, @AuthenticationPrincipal User user) {
        return postService.writePost(request, user);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.PATCH)
    public @ResponseBody
    ResponseEntity<PostDTO> editPost(@RequestBody PostDTO request, @AuthenticationPrincipal User user) {
        PostDTO response = postService.editPost(request, user);
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public @ResponseBody PostDTO getById(@RequestParam int id) {
        return postService.loadPostById(id);
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    public @ResponseBody Page<PostDTO> getPostsByPage(@RequestParam Pageable pageable) {
        return postService.loadPostsByPage(pageable);
    }

    @RequestMapping(value = "/like", method = RequestMethod.POST)
    public @ResponseBody LikesDTO likePost(@RequestBody PostDTO request, @AuthenticationPrincipal User user) {
        return postService.likePost(request, user);
    }
}
