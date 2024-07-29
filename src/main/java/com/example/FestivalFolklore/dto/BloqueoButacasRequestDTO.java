package com.example.FestivalFolklore.dto;

import lombok.Data;
import java.util.List;

@Data
public class BloqueoButacasRequestDTO {
    private Long festivalId;
    private List<Long> butacasIds;
}