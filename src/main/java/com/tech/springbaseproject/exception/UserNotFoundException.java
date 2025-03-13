package com.tech.springbaseproject.exception;

public class UserNotFoundException extends BaseException {
    public UserNotFoundException(Long userId) {
        super(404, "User not found, id=" + userId);
    }
}
