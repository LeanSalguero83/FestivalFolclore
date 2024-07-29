package com.example.FestivalFolklore.repository;

import com.example.FestivalFolklore.model.Sector;
import com.example.FestivalFolklore.model.Fila;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SectorRepository extends JpaRepository<Sector, Long> {
    Optional<Sector> findByFilasContaining(Fila fila);
}