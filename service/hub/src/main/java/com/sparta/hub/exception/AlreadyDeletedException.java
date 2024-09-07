package com.sparta.hub.exception;

public class AlreadyDeletedException extends RuntimeException {
    public AlreadyDeletedException() {
        super();
    }

    public AlreadyDeletedException(String message) {
        super(message);
    }
}
