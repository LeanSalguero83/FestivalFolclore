package com.example.FestivalFolklore.repository;

import com.example.FestivalFolklore.model.Butaca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ButacaRepository extends JpaRepository<Butaca, Long> {
}