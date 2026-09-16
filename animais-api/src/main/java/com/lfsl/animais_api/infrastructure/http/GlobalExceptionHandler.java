package com.lfsl.animais_api.infrastructure.http;

import com.lfsl.animais_api.domain.AnimalNotFoundException;
import com.lfsl.animais_api.infrastructure.http.response.ErrorResponse;
import com.lfsl.animais_api.infrastructure.http.response.ValidationErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;
import java.util.ArrayList;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(AnimalNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(
            AnimalNotFoundException ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(error(404, "Recurso não encontrado",
                        ex.getMessage(), request.getRequestURI()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidation(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        var violations = new ArrayList<ValidationErrorResponse.Violation>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError ->
                violations.add(new ValidationErrorResponse.Violation(
                        fieldError.getField(),
                        fieldError.getDefaultMessage())));

        var response = new ValidationErrorResponse(
                Instant.now(),
                400,
                "Requisição inválida",
                "Existem campos inválidos na requisição",
                request.getRequestURI(),
                violations
        );
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        return ResponseEntity.badRequest().body(error(
                400, "Requisição inválida",
                "O identificador informado deve ser um UUID válido",
                request.getRequestURI()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleMalformedJson(
            HttpMessageNotReadableException ex, HttpServletRequest request) {
        return ResponseEntity.badRequest().body(error(
                400, "Requisição inválida",
                "O corpo da requisição está malformado ou contém um valor inválido (ex.: status fora de DISPONIVEL/ADOTADO)",
                request.getRequestURI()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleDomainRule(
            IllegalArgumentException ex, HttpServletRequest request) {
        return ResponseEntity.badRequest().body(error(
                400, "Requisição inválida", ex.getMessage(),
                request.getRequestURI()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(
            Exception ex, HttpServletRequest request) {
        log.error("Erro inesperado", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(error(500, "Erro interno",
                        "Ocorreu um erro interno inesperado",
                        request.getRequestURI()));
    }

    private ErrorResponse error(int status, String error,
                                String message, String path) {
        return new ErrorResponse(Instant.now(), status, error, message, path);
    }
}