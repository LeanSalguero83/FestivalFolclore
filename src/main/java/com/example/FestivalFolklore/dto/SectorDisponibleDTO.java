package com.example.FestivalFolklore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SectorDisponibleDTO {
    private Long sectorId;
    private String letraSector;
    private String colorSector;
    private List<ButacaDisponibleDTO> butacasDisponibles;
}