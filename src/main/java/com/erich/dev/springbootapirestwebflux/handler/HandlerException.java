package com.erich.dev.springbootapirestwebflux.handler;

import com.erich.dev.springbootapirestwebflux.exception.BadRequestException;
import com.erich.dev.springbootapirestwebflux.exception.NotFoundException;
import com.erich.dev.springbootapirestwebflux.exception.UnauthorizedException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class HandlerException {

    @ExceptionHandler(NotFoundException.class)
    public ProblemDetail notFound(NotFoundException e){
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Not Found");
        problemDetail.setDetail(e.getMessage());
        return problemDetail;
    }



    @ExceptionHandler(WebExchangeBindException.class)
    public ProblemDetail validation(WebExchangeBindException e){
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        Map<String, String> maps = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(f -> {
            String field = f.getField();
            String message = f.getDefaultMessage();
            maps.put("El campo " + field, message);

        });
        problemDetail.setTitle("Validation Error");
        problemDetail.setProperty("errors", maps);
        return problemDetail;
    }


    @ExceptionHandler(BadRequestException.class)
    public ProblemDetail badRequest(BadRequestException e){
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setTitle("Bad Request");
        problemDetail.setDetail(e.getMessage());
        return problemDetail;
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ProblemDetail unauthorized(UnauthorizedException e){
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
        problemDetail.setTitle("UNAUTHORIZED");
        problemDetail.setDetail(e.getMessage());
        return problemDetail;
    }
}
