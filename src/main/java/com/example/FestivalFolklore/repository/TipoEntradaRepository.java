package com.example.FestivalFolklore.repository;

import com.example.FestivalFolklore.model.TipoEntrada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoEntradaRepository extends JpaRepository<TipoEntrada, Long> {
}