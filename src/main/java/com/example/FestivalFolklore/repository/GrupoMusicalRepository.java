package com.example.FestivalFolklore.repository;

import com.example.FestivalFolklore.model.GrupoMusical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrupoMusicalRepository extends JpaRepository<GrupoMusical, Long> {
}