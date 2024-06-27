package com.example.ojt.model.dto.responsewapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseWapper<T> {
    private Integer statusCode;
    private String message;
    private T data;
}
