package kr.ac.jj.algo.exception;

public class RestException extends RuntimeException {
    private final ErrorCode errorCode;

    public RestException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
