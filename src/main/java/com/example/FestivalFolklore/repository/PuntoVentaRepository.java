package com.example.FestivalFolklore.repository;

import com.example.FestivalFolklore.model.PuntoVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PuntoVentaRepository extends JpaRepository<PuntoVenta, Long> {
}