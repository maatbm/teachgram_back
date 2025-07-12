package com.teach3035.teachgram_back.exception.custom;

public class PostNotBelongsToUserException extends RuntimeException {
    public PostNotBelongsToUserException(String message) {
        super(message);
    }
}
