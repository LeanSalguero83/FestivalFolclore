package com.example.FestivalFolklore.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class EntradaDTO {
    private Long id;
    private String numeroTicket;
    private LocalDateTime fechaVenta;
    private BigDecimal monto;
    private String codigoBarras;

}