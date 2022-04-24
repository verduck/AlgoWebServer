package kr.ac.jj.algo.dto;

public class BoardDTO {
    private Long id;
    private String name;
    private String value;
    private String icon;

    public BoardDTO() {}

    public BoardDTO(Long id, String name, String value, String icon) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static class Request {
        private Long id;
        private String name;

        public Request() {}

        public Request(Long id, String name) {
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
    }

    public static class Response {
        private String message;
        private BoardDTO board;

        public Response() {}

        public Response(String message, BoardDTO board) {
            this.message = message;
            this.board = board;
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
