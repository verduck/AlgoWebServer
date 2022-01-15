package com.algo.algoweb.dto;

import org.springframework.data.domain.Page;

public class PostsDTO {
    private boolean success;
    private String message;
    private Page<PostDTO> posts;

    public PostsDTO() {}

    public PostsDTO(boolean success, String message, Page<PostDTO> posts) {
        this.success = success;
        this.message = message;
        this.posts = posts;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Page<PostDTO> getPosts() {
        return posts;
    }

    public void setPosts(Page<PostDTO> posts) {
        this.posts = posts;
    }
}
