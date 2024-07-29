package com.example.FestivalFolklore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Map;

@Data
@AllArgsConstructor
public class CalculoTotalResponseDTO {
    private Map<String, BigDecimal> subtotalesPorTipo;
    private BigDecimal total;
}