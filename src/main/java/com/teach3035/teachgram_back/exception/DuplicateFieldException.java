package com.teach3035.teachgram_back.exception;

public class DuplicateFieldException extends RuntimeException {
    public DuplicateFieldException(String message) {
        super(message);
    }
}
