package ca.devpro.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
public class ValidationException extends RuntimeException {

    @Getter
    private final Map<String, String> errors;
}
