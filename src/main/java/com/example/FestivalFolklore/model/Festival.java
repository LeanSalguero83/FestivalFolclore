package com.example.FestivalFolklore.model;

import com.example.FestivalFolklore.dto.SectorDisponibleDTO;
import com.example.FestivalFolklore.dto.TipoEntradaDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.example.FestivalFolklore.exception.EntityNotFoundException;

@Entity
@Table(name = "Festival")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Festival {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "anioEdicion", nullable = false)
    private Integer anioEdicion;

    @Column(name = "descuentoVentaAnticipada", nullable = false)
    private Double descuentoVentaAnticipada;

    @Column(name = "fechaInicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "porcentajeDevolucionPorAnulacion", nullable = false)
    private Double porcentajeDevolucionPorAnulacion;

    @Column(name = "vigente", nullable = false)
    private Boolean vigente;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "festival_id")
    private List<DiaFestival> diasFestival;

    public boolean estaVigente() {
        return this.vigente;
    }

    public List<SectorDisponibleDTO> buscarSectoresDisponiblesParaDias(LocalDate fecha) {
        return this.diasFestival.stream()
                .filter(dia -> dia.getFecha().equals(fecha))
                .findFirst()
                .map(DiaFestival::mostrarSectoresDisponibles)
                .orElseThrow(() -> new EntityNotFoundException("DiaFestival", "fecha: " + fecha));
    }

    public List<TipoEntradaDTO> buscarTiposDeEntradaParaDias(LocalDate fecha) {
        return this.diasFestival.stream()
                .filter(dia -> dia.getFecha().equals(fecha))
                .findFirst()
                .map(DiaFestival::getTiposDeEntrada)
                .orElseThrow(() -> new EntityNotFoundException("DiaFestival", "fecha: " + fecha));
    }

    public boolean validarFechaParaVtaAnticipada(LocalDateTime fechaEvento, LocalDateTime fechaActual) {
        DiaFestival diaFestival = this.diasFestival.stream()
                .filter(dia -> dia.getFecha().equals(fechaEvento.toLocalDate()))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("DiaFestival", "fecha: " + fechaEvento));

        return diaFestival.verificarVtoFechaAnticipada(fechaActual);
    }

    public void bloquearButacaParaDias(List<DisponibilidadButaca> butacasABloquear, Estado estadoBloqueado) {
        for (DiaFestival dia : this.diasFestival) {
            dia.bloquearButaca(butacasABloquear, estadoBloqueado);
        }
    }

    public BigDecimal obtenerPrecioEntradaParaDiaSectorYTipoDeEntrada(LocalDate fecha, Long sectorId, String tipoEntrada) {
        return this.diasFestival.stream()
                .filter(dia -> dia.getFecha().equals(fecha))
                .findFirst()
                .map(dia -> dia.obtenerPrecioParaSectorYTipoDeEntrada(sectorId, tipoEntrada))
                .orElseThrow(() -> new EntityNotFoundException("DiaFestival", "fecha: " + fecha));
    }

    public void venderButacas(List<DisponibilidadButaca> butacasAVender, Estado estadoVendido) {
        for (DiaFestival dia : this.diasFestival) {
            dia.venderButacas(butacasAVender, estadoVendido);
        }
    }

}