package com.example.FestivalFolklore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VerificacionVentaAnticipadaDTO {
    private boolean esVentaAnticipada;
    private LocalDateTime fechaVerificacion;
}