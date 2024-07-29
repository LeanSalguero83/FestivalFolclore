package com.example.FestivalFolklore.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "Estado")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Estado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    public boolean esDisponible() {
        return "Disponible".equals(this.nombre);
    }

    public boolean esBloqueada() {
        return "Bloqueado".equals(this.nombre);
    }

    public boolean esVendida() {
        return "Vendido".equals(this.nombre);
    }


}