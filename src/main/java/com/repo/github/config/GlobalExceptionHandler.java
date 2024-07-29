package com.repo.github.config;

import com.repo.github.exception.RepoNotFound;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<RepoNotFound> handleWebClientResponseException(WebClientResponseException ex) {
        RepoNotFound repoNotFound = new RepoNotFound(
                ex.getStatusCode().value(),
                ex.getMessage()
        );
        return new ResponseEntity<>(repoNotFound, ex.getStatusCode());
    }
}
