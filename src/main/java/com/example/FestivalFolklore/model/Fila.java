package com.example.FestivalFolklore.model;

import com.example.FestivalFolklore.dto.ButacaDisponibleDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Fila")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Fila {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero", nullable = false)
    private Integer numero;

    @OneToMany(mappedBy = "fila", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Butaca> butacas;

    @ManyToOne
    @JoinColumn(name = "sector_id", nullable = false)
    private Sector sector;

    public ButacaDisponibleDTO getDatosSector(Butaca butaca) {
        return new ButacaDisponibleDTO(
                sector.getId(),
                sector.getLetraIdentificatoria(),
                sector.getColorIdentificatorio(),
                this.numero,
                butaca.getNumero()
        );
    }

}