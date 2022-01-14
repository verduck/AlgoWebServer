package com.algo.algoweb.dto;

import java.time.LocalDate;

public class PostDTO {
  private Integer id;
  private String title;
  private String content;
  private UserDTO author;
  private LocalDate createdAt;
  private LocalDate updatedAt;

  public PostDTO () {}

  public PostDTO(Integer id, String title, String content, UserDTO author, LocalDate createdAt, LocalDate updatedAt) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.author = author;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public UserDTO getAuthor() {
    return author;
  }

  public void setAuthor(UserDTO author) {
    this.author = author;
  }

  public LocalDate getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDate createdAt) {
    this.createdAt = createdAt;
  }

  public LocalDate getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDate updatedAt) {
    this.updatedAt = updatedAt;
  }

  public static class Request {
    private String title;
    private String content;

    public Request() {}

    public Request(Integer id, String title, String content) {
      this.title = title;
      this.content = content;
    }

    public String getTitle() {
      return title;
    }

    public void setTitle(String title) {
      this.title = title;
    }

    public String getContent() {
      return content;
    }

    public void setContent(String content) {
      this.content = content;
    }
  }

  public static class Response {
    private boolean success;
    private String message;
    private PostDTO post;

    public Response() {}

    public Response(boolean success, String message, PostDTO post) {
      this.success = success;
      this.message = message;
      this.post = post;
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

    public PostDTO getPost() {
      return post;
    }

    public void setPost(PostDTO post) {
      this.post = post;
    }
  }

}
