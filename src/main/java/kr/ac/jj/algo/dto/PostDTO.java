package kr.ac.jj.algo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class PostDTO {
    private Integer id;
    private String title;
    @JsonProperty("author")
    private UserDTO user;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long numLikes;

    public PostDTO () {}

    public PostDTO(Integer id, String title, UserDTO user, LocalDateTime createdAt, LocalDateTime updatedAt, Long numLikes) {
        this.id = id;
        this.title = title;
        this.user = user;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.numLikes = numLikes;
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

    public Long getNumLikes() {
    return numLikes;
    }

    public void setNumLikes(Long numLikes) {
    this.numLikes = numLikes;
    }

    public static class Creation {
        private String title;
        private String content;
        private BoardDTO.Request board;

        public Creation() {}

        public Creation(String title, String content, BoardDTO.Request board) {
          this.title = title;
          this.content = content;
          this.board = board;
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

        public BoardDTO.Request getBoard() {
          return board;
        }

        public void setBoard(BoardDTO.Request board) {
          this.board = board;
        }
    }

    public static class Patch {
        private String title;
        private String content;

        public Patch() {}
        public Patch(String title, String content) {
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

    public static class Detail {
        private Integer id;
        private String title;
        private String content;
        private BoardDTO board;
        @JsonProperty("author")
        private UserDTO user;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private Long numLikes;

        public Detail() {}

        public Detail(Integer id, String title, String content, BoardDTO board, UserDTO user, LocalDateTime createdAt, LocalDateTime updatedAt, Long numLikes) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.board = board;
            this.user = user;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.numLikes = numLikes;
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

        public BoardDTO getBoard() {
            return board;
        }

        public void setBoard(BoardDTO board) {
            this.board = board;
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

        public Long getNumLikes() {
            return numLikes;
        }

        public void setNumLikes(Long numLikes) {
            this.numLikes = numLikes;
        }
    }

}
