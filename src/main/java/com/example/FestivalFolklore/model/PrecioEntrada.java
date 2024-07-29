package com.example.FestivalFolklore.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "PrecioEntrada")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PrecioEntrada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "monto", nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    @ManyToOne
    @JoinColumn(name = "diaFestival_id", nullable = false)
    private DiaFestival diaFestival;

    @ManyToOne
    @JoinColumn(name = "tipoEntrada_id", nullable = false)
    private TipoEntrada tipoEntrada;

    @ManyToOne
    @JoinColumn(name = "sector_id", nullable = false)
    private Sector sector;

    public boolean esSectorYTipo(Long sectorId, String tipoEntrada) {
        return this.sector.getId().equals(sectorId) &&
                this.tipoEntrada.getNombre().equals(tipoEntrada);
    }


}