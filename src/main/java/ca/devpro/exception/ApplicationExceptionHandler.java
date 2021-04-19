package ca.devpro.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @Autowired
    private MessageSourceAccessor messageSourceAccessor;

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(ValidationException ex) {
        return ResponseEntity.badRequest()
                .body(ex.getErrors()
                        .entrySet()
                        .stream()
                        .collect(Collectors.toMap(Map.Entry::getKey,e -> translate(e.getValue()))));
    }

    private String translate(String code) {
        return messageSourceAccessor.getMessage(code, code, LocaleContextHolder.getLocale());
    }
}
