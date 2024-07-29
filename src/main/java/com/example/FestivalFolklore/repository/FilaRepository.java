package com.example.FestivalFolklore.repository;

import com.example.FestivalFolklore.model.Butaca;
import com.example.FestivalFolklore.model.Fila;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FilaRepository extends JpaRepository<Fila, Long> {
    Optional<Fila> findByButacasContaining(Butaca butaca);

}