package com.example.ojt.advice;

import com.example.ojt.exception.*;
import com.example.ojt.model.dto.response.ResponseError;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class APIControllerAdvice {

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String,Object> forbidden(AccessDeniedException e){
        Map<String,Object> map = new HashMap<>();
        map.put("error",new ResponseError(403,"FOR_BIDDEN",e));
        return  map;
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> invalidRequest(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        });

        Map<String, Object> response = new HashMap<>();
        response.put("error", "Validation Failed");
        response.put("status", HttpStatus.BAD_REQUEST.value());
        response.put("message", errors);
        return response;
    }

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public Map<String, Object> handleCustomException(CustomException ex, HttpServletResponse response) throws IOException {
        response.setStatus(ex.getHttpStatus().value());
        Map<String, Object> map = new HashMap<>();
        map.put("error", "Bad Request");
        map.put("statusCode", ex.getHttpStatus().value());
        map.put("message", ex.getMessage());
        return map;
    }



    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String,Object> handleNotFoundException(NotFoundException ex) {
        Map<String,Object> map = new HashMap<>();
        map.put("error", "Bad Request");
        map.put("statusCode", 400);
        map.put("message", ex.getMessage());
        Map<String,Object> response = new HashMap<>();
        response.put("Fields Error",map);
        return response;
    }

    @ExceptionHandler(AccountLockedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public Map<String,Object> handelAccountLockedException(AccountLockedException e){
        Map<String,Object> map = new HashMap<>();
        map.put("error", "Forbidden");
        map.put("statusCode", 403);
        map.put("message", e.getMessage());
        Map<String,Object> response = new HashMap<>();
        response.put("Fields Error",map);
        return response;
    }

    @ExceptionHandler(RequestErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String,Object> handelRequestErrorException(RequestErrorException e){
        Map<String,Object> map = new HashMap<>();
        map.put("statusCode", 400);
        map.put("message", e.getMessage());
        map.put("error", "Bad Request");

        Map<String,Object> response = new HashMap<>();
        response.put("Fields Error",map);
        return response;
    }
}
