package com.example.FestivalFolklore.repository;

import com.example.FestivalFolklore.model.PrecioEntrada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrecioEntradaRepository extends JpaRepository<PrecioEntrada, Long> {
}