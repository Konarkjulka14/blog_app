package com.springbootblog.exception;

import org.springframework.http.HttpStatus;

public class BlogAPIException extends RuntimeException{

    HttpStatus httpStatus;
    String message;

    public BlogAPIException(HttpStatus httpStatus, String message) {
        httpStatus = httpStatus;
        this.message = message;
    }

    public BlogAPIException(String message, HttpStatus httpStatus, String message1) {
        super(message);
        httpStatus = this.httpStatus;
        this.message = message1;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

