package com.example.FestivalFolklore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ButacaDisponibleDTO {
    private Long sectorId;
    private String letraSector;
    private String colorSector;
    private Integer numeroFila;
    private Integer numeroButaca;
}