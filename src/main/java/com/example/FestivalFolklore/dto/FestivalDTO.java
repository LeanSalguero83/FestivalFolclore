package com.example.FestivalFolklore.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FestivalDTO {
    private Long id;
    private String nombre;
    private Integer anioEdicion;
    private LocalDate fechaInicio;

}