package com.example.FestivalFolklore.repository;

import com.example.FestivalFolklore.model.Actuacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActuacionRepository extends JpaRepository<Actuacion, Long> {
}