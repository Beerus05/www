package com.example.eshopapi.dto;

import jakarta.validation.constraints.NotNull;

public record UpdateStockRequest(
    @NotNull(message = "delta không được để trống")
    Integer delta
) {}
