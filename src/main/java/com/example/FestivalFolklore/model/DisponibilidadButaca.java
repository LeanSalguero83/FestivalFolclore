package com.example.FestivalFolklore.model;

import com.example.FestivalFolklore.dto.ButacaDisponibleDTO;
import com.example.FestivalFolklore.exception.InvalidOperationException;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "DisponibilidadButaca")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DisponibilidadButaca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "butaca_id", nullable = false)
    private Butaca butaca;

    @ManyToOne
    @JoinColumn(name = "estado_id", nullable = false)
    private Estado estado;


    public boolean estaDisponible() {
        return estado.esDisponible();
    }

    public ButacaDisponibleDTO getUbicacionCompleta() {
        return butaca.getDatosSector();
    }

    public void bloquear(Estado estadoBloqueado) {
        if (this.estado.esBloqueada()) {
            throw new InvalidOperationException("La butaca ya está bloqueada");
        }
        setEstado(estadoBloqueado);
    }

    public void venderButaca(Estado estadoVendido) {
        if (this.estado.esVendida()) {
            throw new InvalidOperationException("La butaca ya está vendida");
        }
        setEstado(estadoVendido);
    }





}