package kr.ac.jj.algo.dto;

public class LikesDTO {
    private Integer id;
    private UserDTO user;
    private PostDTO post;

    public LikesDTO() {}

    public LikesDTO(Integer id, UserDTO user, PostDTO post) {
        this.id = id;
        this.user = user;
        this.post = post;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public static class Response {
        private boolean success;
        private String message;
        private LikesDTO likes;

        public Response() {}

        public Response(boolean success, String message, LikesDTO likes) {
            this.success = success;
            this.message = message;
            this.likes = likes;
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

        public LikesDTO getLikes() {
            return likes;
        }

        public void setLikes(LikesDTO likes) {
            this.likes = likes;
        }
    }
}
