package kr.ac.jj.algo.dto;

import java.time.LocalDateTime;

public class ReplyDTO {
    private Long id;
    private String content;
    private UserDTO user;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ReplyDTO() {}

    public ReplyDTO(Long id, String content, UserDTO user, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.content = content;
        this.user = user;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static class Request {
        private String content;
        private UserDTO user;
        private PostDTO post;
        private ReplyDTO reply;

        public Request() {}

        public Request(String content, UserDTO user, PostDTO post, ReplyDTO reply) {
            this.content = content;
            this.user = user;
            this.post = post;
            this.reply = reply;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public UserDTO getUser() {
            return user;
        }

        public void setUser(UserDTO user) {
            this.user = user;
        }

        public PostDTO getPost() {
            return post;
        }

        public void setPost(PostDTO post) {
            this.post = post;
        }

        public ReplyDTO getReply() {
            return reply;
        }

        public void setReply(ReplyDTO reply) {
            this.reply = reply;
        }
    }

    public static class Response {
        private boolean success;
        private String message;
        private ReplyDTO reply;

        public Response() {}

        public Response(boolean success, String message, ReplyDTO reply) {
            this.success = success;
            this.message = message;
            this.reply = reply;
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

        public ReplyDTO getReply() {
            return reply;
        }

        public void setReply(ReplyDTO reply) {
            this.reply = reply;
        }
    }
}
