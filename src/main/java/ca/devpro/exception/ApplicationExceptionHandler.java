package ca.devpro.exception;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@AllArgsConstructor
public class ApplicationExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(ValidationException ex) {
        Map<String, String> translatedErrors = ex.getErrors()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> translate(e.getValue())));


        return ResponseEntity.badRequest()
                .body(translatedErrors);
    }

    private String translate(String code) {
        return messageSource.getMessage(code, new Object[]{}, code, LocaleContextHolder.getLocale());
    }
}
