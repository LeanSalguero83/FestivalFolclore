package com.example.FestivalFolklore.repository;

import com.example.FestivalFolklore.model.CentroDeVenta;
import com.example.FestivalFolklore.model.PuntoVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CentroDeVentaRepository extends JpaRepository<CentroDeVenta, Long> {
    Optional<CentroDeVenta> findByPuntosVentaContaining(PuntoVenta puntoVenta);

}