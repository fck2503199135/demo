package com.example.demo.exception;

/**
 * Created by luoanan
 * 2020-03-30 10:41
 */
public class AuthorizeException extends RuntimeException {

    public AuthorizeException(String message) {
        super(message);
    }
}
