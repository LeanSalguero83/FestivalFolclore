package com.example.FestivalFolklore.repository;

import com.example.FestivalFolklore.model.DiaFestival;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaFestivalRepository extends JpaRepository<DiaFestival, Long> {
}