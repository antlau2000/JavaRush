package com.space.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UncorrectedInputDataException extends Exception {
    public UncorrectedInputDataException() {
    }

    public UncorrectedInputDataException(String message) {
        super(message);
    }

    public UncorrectedInputDataException(String message, Throwable cause) {
        super(message, cause);
    }

    public UncorrectedInputDataException(Throwable cause) {
        super(cause);
    }
}