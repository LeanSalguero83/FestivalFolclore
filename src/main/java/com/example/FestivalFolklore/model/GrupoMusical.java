package com.example.FestivalFolklore.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "GrupoMusical")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GrupoMusical {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cantIntegrantes", nullable = false)
    private Integer cantIntegrantes;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
}