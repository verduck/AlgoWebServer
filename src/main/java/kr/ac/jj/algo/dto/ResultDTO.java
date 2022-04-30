package kr.ac.jj.algo.dto;

public class ResultDTO {
    private String message;

    public ResultDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
