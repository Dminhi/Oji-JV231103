package com.example.ojt.config;

import com.example.ojt.exception.CustomException;
import org.springframework.http.HttpStatus;


public class StringToInteger {
    public Integer parse(String text) throws NumberFormatException, CustomException {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            throw new CustomException("Don't convert String to Integer", HttpStatus.BAD_REQUEST);
        }
    }
}
