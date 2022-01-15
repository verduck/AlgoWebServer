package com.algo.algoweb.dto;

public class BoardDTO {
    private Long id;
    private String name;

    public BoardDTO() {}

    public BoardDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class Response {
        private boolean success;
        private String message;
        private BoardDTO board;

        public Response() {}

        public Response(boolean success, String message, BoardDTO board) {
            this.success = success;
            this.message = message;
            this.board = board;
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String isMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public BoardDTO getBoard() {
            return board;
        }

        public void setBoard(BoardDTO board) {
            this.board = board;
        }
    }
}
