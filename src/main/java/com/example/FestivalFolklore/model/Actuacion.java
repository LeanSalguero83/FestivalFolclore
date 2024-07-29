package com.example.FestivalFolklore.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "Actuacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Actuacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "duracionActuacion", nullable = false)
    private Integer duracionActuacion;

    @Column(name = "horaInicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "numeroOrden", nullable = false)
    private Integer numeroOrden;

    @ManyToOne
    @JoinColumn(name = "grupoMusical_id")
    private GrupoMusical grupoMusical;
}