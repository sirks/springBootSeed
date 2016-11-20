package io.seed.app;

import org.springframework.http.HttpStatus;

import static io.seed.app.ApiException.ErrorCode.ERROR;

public class ApiException extends RuntimeException {

    private HttpStatus status;
    private ErrorBean error;

    private ApiException(HttpStatus status, ErrorBean error) {
        this.status = status;
        this.error = error;
    }

    public static ApiException apiException(HttpStatus status) {
        return new ApiException(status, new ErrorBean(ERROR));
    }

    public static ApiException walException(HttpStatus status, ErrorCode code, String msg) {
        return new ApiException(status, new ErrorBean(code, msg));
    }

    public static ApiException apiException(HttpStatus status, String msg) {
        return walException(status, ERROR, msg);
    }

    public HttpStatus getStatus() {
        return status;
    }

    public ErrorBean getError() {
        return error;
    }
    
    public static class ErrorBean{
	    private ErrorCode code;
	    private String msg;

	    public ErrorBean() {
	    }

	    public ErrorBean(ErrorCode code) {
		    this.code = code;
	    }

	    public ErrorBean(String msg) {
		    this.msg = msg;
	    }

	    public ErrorBean(ErrorCode code, String msg) {
		    this.code = code;
		    this.msg = msg;
	    }

	    public ErrorCode getCode() {
		    return code;
	    }

	    public void setCode(ErrorCode code) {
		    this.code = code;
	    }

	    public String getMsg() {
		    return msg;
	    }

	    public void setMsg(String msg) {
		    this.msg = msg;
	    }

    }
	public enum ErrorCode {
		ERROR, INSUFFICIENT
	}
}
