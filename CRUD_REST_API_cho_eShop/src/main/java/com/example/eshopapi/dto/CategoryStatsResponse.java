package com.example.eshopapi.dto;

import java.math.BigDecimal;

public record CategoryStatsResponse(
    Long id,
    String name,
    Long productCount,
    BigDecimal totalStockValue
) {
    public CategoryStatsResponse(Long id, String name, Long productCount, Number totalStockValue) {
        this(id, name, productCount, totalStockValue != null ? BigDecimal.valueOf(totalStockValue.doubleValue()) : BigDecimal.ZERO);
    }
}
