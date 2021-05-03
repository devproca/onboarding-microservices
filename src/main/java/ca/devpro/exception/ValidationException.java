package ca.devpro.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
@Getter
public class ValidationException extends RuntimeException {

    private final Map<String, String> errors;
}
