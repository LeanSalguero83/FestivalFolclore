package com.example.FestivalFolklore.repository;

import com.example.FestivalFolklore.model.DisponibilidadButaca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisponibilidadButacaRepository extends JpaRepository<DisponibilidadButaca, Long> {
}