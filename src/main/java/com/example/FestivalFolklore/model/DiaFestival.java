package com.example.FestivalFolklore.model;

import com.example.FestivalFolklore.dto.ButacaDisponibleDTO;
import com.example.FestivalFolklore.dto.SectorDisponibleDTO;
import com.example.FestivalFolklore.dto.TipoEntradaDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.example.FestivalFolklore.exception.EntityNotFoundException;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "DiaFestival")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiaFestival {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "completa", nullable = false)
    private Boolean completa;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "fechaLimiteAnulacionEntrada", nullable = false)
    private LocalDate fechaLimiteAnulacionEntrada;

    @Column(name = "fechaVtoVtaAnticipada", nullable = false)
    private LocalDate fechaVtoVtaAnticipada;

    @Column(name = "horaPresentacion", nullable = false)
    private LocalTime horaPresentacion;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "diaFestival_id")
    private List<Actuacion> actuaciones;

    @OneToMany(mappedBy = "diaFestival", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PrecioEntrada> preciosEntrada;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "diaFestival_id")
    private List<DisponibilidadButaca> disponibilidadesButaca;

    public List<SectorDisponibleDTO> mostrarSectoresDisponibles() {
        return new ArrayList<>(this.disponibilidadesButaca.stream()
                .filter(DisponibilidadButaca::estaDisponible)
                .map(this::buscarDatosUbicacionButaca)
                .collect(Collectors.groupingBy(
                        ButacaDisponibleDTO::getSectorId,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                butacas -> new SectorDisponibleDTO(
                                        butacas.get(0).getSectorId(),
                                        butacas.get(0).getLetraSector(),
                                        butacas.get(0).getColorSector(),
                                        butacas
                                )
                        )
                ))
                .values());
    }

    private ButacaDisponibleDTO buscarDatosUbicacionButaca(DisponibilidadButaca disponibilidadButaca) {
        return disponibilidadButaca.getUbicacionCompleta();
    }


    public List<TipoEntradaDTO> getTiposDeEntrada() {
        return this.preciosEntrada.stream()
                .map(PrecioEntrada::getTipoEntrada)
                .distinct()
                .map(tipoEntrada -> new TipoEntradaDTO(tipoEntrada.getId(), tipoEntrada.getNombre()))
                .collect(Collectors.toList());
    }

    public boolean verificarVtoFechaAnticipada(LocalDateTime fechaActual) {
        LocalDateTime fechaVtoVtaAnticipada = this.fechaVtoVtaAnticipada.atStartOfDay();
        return fechaActual.isBefore(fechaVtoVtaAnticipada);
    }


    public void bloquearButaca(List<DisponibilidadButaca> butacasABloquear, Estado estadoBloqueado) {
        for (DisponibilidadButaca disponibilidad : this.disponibilidadesButaca) {
            if (butacasABloquear.contains(disponibilidad)) {
                disponibilidad.bloquear(estadoBloqueado);
            }
        }
    }

    public BigDecimal obtenerPrecioParaSectorYTipoDeEntrada(Long sectorId, String tipoEntrada) {
        return this.preciosEntrada.stream()
                .filter(precio -> precio.esSectorYTipo(sectorId, tipoEntrada))
                .findFirst()
                .map(PrecioEntrada::getMonto)
                .orElseThrow(() -> new EntityNotFoundException("PrecioEntrada", "sectorId: " + sectorId + ", tipoEntrada: " + tipoEntrada));
    }

    public void venderButacas(List<DisponibilidadButaca> butacasAVender, Estado estadoVendido) {
        for (DisponibilidadButaca disponibilidad : this.disponibilidadesButaca) {
            if (butacasAVender.contains(disponibilidad)) {
                disponibilidad.venderButaca(estadoVendido);
            }
        }
    }

}