package com.example.FestivalFolklore.controller;

import com.example.FestivalFolklore.dto.*;
import com.example.FestivalFolklore.model.Festival;
import com.example.FestivalFolklore.service.GestorRegVentaEntradas;
import com.example.FestivalFolklore.util.ValidationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/venta-entradas")
public class VentaEntradasController {

    @Autowired
    private GestorRegVentaEntradas gestorRegVentaEntradas;

    @GetMapping("/festivales-vigentes")
    public ResponseEntity<List<FestivalDTO>> obtenerFestivalesVigentes() {
        List<Festival> festivalesVigentes = gestorRegVentaEntradas.buscarFestivalesVigentes();
        List<FestivalDTO> festivalesDTO = festivalesVigentes.stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(festivalesDTO);
    }

    @GetMapping("/fechas-festival/{festivalId}")
    public ResponseEntity<List<FechaFestivalDTO>> obtenerFechasFestival(@PathVariable Long festivalId) {
        ValidationUtils.validatePositiveId(festivalId, "festival");
        List<LocalDate> fechas = gestorRegVentaEntradas.buscarFechasFestival(festivalId);
        List<FechaFestivalDTO> fechasDTO = fechas.stream()
                .map(this::convertirAFechaDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(fechasDTO);
    }

    @GetMapping("/sectores-disponibles/{festivalId}")
    public ResponseEntity<List<SectorDisponibleDTO>> obtenerSectoresDisponibles(
            @PathVariable Long festivalId,
            @RequestParam LocalDate fecha) {
        ValidationUtils.validatePositiveId(festivalId, "festival");
        List<SectorDisponibleDTO> sectoresDisponibles = gestorRegVentaEntradas.buscarSectoresDisponibles(festivalId, fecha);
        return ResponseEntity.ok(sectoresDisponibles);
    }

    @GetMapping("/tipos-entrada/{festivalId}")
    public ResponseEntity<List<TipoEntradaDTO>> obtenerTiposEntrada(
            @PathVariable Long festivalId,
            @RequestParam LocalDate fecha) {
        ValidationUtils.validatePositiveId(festivalId, "festival");
        List<TipoEntradaDTO> tiposEntrada = gestorRegVentaEntradas.buscarTiposDeEntrada(festivalId, fecha);
        return ResponseEntity.ok(tiposEntrada);
    }

    @GetMapping("/verificar-venta-anticipada/{festivalId}")
    public ResponseEntity<VerificacionVentaAnticipadaDTO> verificarVentaAnticipada(
            @PathVariable Long festivalId,
            @RequestParam LocalDateTime fechaEvento) {
        ValidationUtils.validatePositiveId(festivalId, "festival");
        VerificacionVentaAnticipadaDTO verificacion = gestorRegVentaEntradas.verificarVentaAnticipada(festivalId, fechaEvento);
        return ResponseEntity.ok(verificacion);
    }

    @PostMapping("/bloquear-butacas")
    public ResponseEntity<Void> bloquearButacas(@RequestBody BloqueoButacasRequestDTO request) {
        gestorRegVentaEntradas.bloquearButaca(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/calcular-total")
    public ResponseEntity<CalculoTotalResponseDTO> calcularTotal(@RequestBody CalculoTotalRequestDTO request) {
        CalculoTotalResponseDTO response = gestorRegVentaEntradas.calcularTotal(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/crear-entrada")
    public ResponseEntity<EntradaDTO> crearEntrada(@RequestBody CrearEntradaRequestDTO requestDTO) {
        EntradaDTO entradaCreada = gestorRegVentaEntradas.crearEntrada(requestDTO);
        return ResponseEntity.ok(entradaCreada);
    }


    private FestivalDTO convertirADTO(Festival festival) {
        FestivalDTO dto = new FestivalDTO();
        dto.setId(festival.getId());
        dto.setNombre(festival.getNombre());
        dto.setAnioEdicion(festival.getAnioEdicion());
        dto.setFechaInicio(festival.getFechaInicio());
        return dto;
    }

    private FechaFestivalDTO convertirAFechaDTO(LocalDate fecha) {
        FechaFestivalDTO dto = new FechaFestivalDTO();
        dto.setFecha(fecha);
        return dto;
    }
}