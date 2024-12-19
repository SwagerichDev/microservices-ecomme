package com.erich.dev.cust.handler;

import com.erich.dev.cust.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalHandlerException {


    @ExceptionHandler
    public ProblemDetail handlerNotFoundException(NotFoundException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setStatus(HttpStatus.NOT_FOUND);
        problemDetail.setDetail(e.getMessage());
        return problemDetail;
    }

    @ExceptionHandler
    public ProblemDetail methodArgumentNotValidException(MethodArgumentNotValidException e) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
//        Map<String, Object> errors = new HashMap<>();
        Map<String, Object> errors = e.getBindingResult().getAllErrors().stream().collect(Collectors.toMap(error -> ((FieldError)error).getField(), error -> "El cuerpo" + error.getDefaultMessage()));
//        e.getBindingResult().getAllErrors().forEach(error -> {
//            var field = ((FieldError) error).getField();
//            var message = "El cuerpo" + error.getDefaultMessage();
//            errors.put(field, message);
//        });
        problemDetail.setStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setProperty("errors", errors);
        return problemDetail;
    }
}
