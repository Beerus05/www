package com.example.eshopapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorResponse(
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    LocalDateTime timestamp,
    int status,
    String error,
    String message,
    String path,
    Map<String, String> errors
) {}
