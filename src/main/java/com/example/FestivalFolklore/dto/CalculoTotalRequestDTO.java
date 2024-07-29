package com.example.FestivalFolklore.dto;

import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class CalculoTotalRequestDTO {
    private Long festivalId;
    private LocalDate fecha;
    private List<ButacaSeleccionadaDTO> butacasSeleccionadas;
}