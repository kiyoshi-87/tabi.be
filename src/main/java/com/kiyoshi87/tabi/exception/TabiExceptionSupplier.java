package com.kiyoshi87.tabi.exception;

import lombok.NoArgsConstructor;

import java.util.function.Supplier;

import static com.kiyoshi87.tabi.exception.TabiExceptionType.BAD_REQUEST;
import static com.kiyoshi87.tabi.exception.TabiExceptionType.INTERNAL_ERROR;
import static com.kiyoshi87.tabi.exception.TabiExceptionType.NOT_FOUND;
import static com.kiyoshi87.tabi.exception.TabiExceptionType.VALIDATION_ERROR;

@NoArgsConstructor
public class TabiExceptionSupplier extends Throwable {

    public static Supplier<TabiApiException> badRequest(String message) {
        return () -> new TabiApiException(BAD_REQUEST, message);
    }

    public static Supplier<TabiApiException> notFound(String message) {
        return () -> new TabiApiException(NOT_FOUND, message);
    }

    public static Supplier<TabiApiException> validationError(String message) {
        return () -> new TabiApiException(VALIDATION_ERROR, message);
    }

    public static Supplier<TabiApiException> internalError(String message) {
        return () -> new TabiApiException(INTERNAL_ERROR, message);
    }
}
