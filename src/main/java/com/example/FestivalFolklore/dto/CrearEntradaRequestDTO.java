package com.example.FestivalFolklore.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CrearEntradaRequestDTO {
    private Long festivalId;
    private Long butacaId;
    private Long puntoVentaId;
    private Long precioEntradaId;
    private BigDecimal monto;
}