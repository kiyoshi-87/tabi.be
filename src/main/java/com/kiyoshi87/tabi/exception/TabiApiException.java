package com.kiyoshi87.tabi.exception;

import lombok.Getter;

@Getter
public class TabiApiException extends RuntimeException {
    private final TabiExceptionType type;
    private final String message;

    public TabiApiException(TabiExceptionType type, String message) {
        super(message);
        this.type = type;
        this.message = message;
    }
}
