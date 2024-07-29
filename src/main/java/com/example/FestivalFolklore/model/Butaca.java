package com.example.FestivalFolklore.model;

import com.example.FestivalFolklore.dto.ButacaDisponibleDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "Butaca")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Butaca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero", nullable = false)
    private Integer numero;
    @ManyToOne
    @JoinColumn(name = "fila_id", nullable = false)
    private Fila fila;

    public ButacaDisponibleDTO getDatosSector() {
        return fila.getDatosSector(this);
    }



}