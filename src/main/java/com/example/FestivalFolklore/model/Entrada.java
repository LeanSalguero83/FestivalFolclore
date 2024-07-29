package com.example.FestivalFolklore.model;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Entrada")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Entrada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigoBarras", nullable = false, unique = true, length = 50)
    private String codigoBarras;

    @Column(name = "fechaVenta", nullable = false)
    private LocalDateTime fechaVenta;

    @Column(name = "monto", nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    @Column(name = "numeroTicket", nullable = false, unique = true, length = 50)
    private String numeroTicket;

    @ManyToOne
    @JoinColumn(name = "disponibilidadButaca_id", nullable = false)
    private DisponibilidadButaca disponibilidadButaca;

    @ManyToOne
    @JoinColumn(name = "puntoVenta_id", nullable = false)
    private PuntoVenta puntoVenta;

    @ManyToOne
    @JoinColumn(name = "precioEntrada_id", nullable = false)
    private PrecioEntrada precioEntrada;
}