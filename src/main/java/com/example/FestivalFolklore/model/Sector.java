package com.example.FestivalFolklore.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Sector")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "colorIdentificatorio", nullable = false, length = 20)
    private String colorIdentificatorio;

    @Column(name = "letraIdentificatoria", nullable = false, length = 1)
    private String letraIdentificatoria;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "ubicacion", length = 100)
    private String ubicacion;

    @OneToMany(mappedBy = "sector", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Fila> filas;



}