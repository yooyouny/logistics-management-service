package com.sparta.hub.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDto<T> {

    private String resultCode;
    private String resultMessage;
    private T data;
}
