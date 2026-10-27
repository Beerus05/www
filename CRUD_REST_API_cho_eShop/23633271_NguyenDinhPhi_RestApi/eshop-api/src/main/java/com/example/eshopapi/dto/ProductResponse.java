package com.example.eshopapi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponse(
    Long id,
    String name,
    String description,
    BigDecimal price,
    Integer quantity,
    String imageUrl,
    Long categoryId,
    String categoryName,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime createdAt
) {}
