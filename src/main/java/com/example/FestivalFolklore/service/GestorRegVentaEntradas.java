package com.example.FestivalFolklore.service;

import com.example.FestivalFolklore.dto.*;
import com.example.FestivalFolklore.model.*;
import com.example.FestivalFolklore.repository.*;
import com.example.FestivalFolklore.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GestorRegVentaEntradas {

    @Autowired
    private FestivalRepository festivalRepository;

    @Autowired
    private SectorRepository sectorRepository;

    @Autowired
    private FilaRepository filaRepository;

    @Autowired
    private EstadoRepository estadoRepository;
    @Autowired
    private EntradaRepository entradaRepository;
    @Autowired
    private PuntoVentaRepository puntoVentaRepository;
    @Autowired
    private ImpresorEntrada impresorEntrada;
    @Autowired
    private CentroDeVentaRepository centroDeVentaRepository;


    @Autowired
    private PrecioEntradaRepository precioEntradaRepository;

    @Autowired
    private DisponibilidadButacaRepository disponibilidadButacaRepository;

    public List<Festival> buscarFestivalesVigentes() {
        List<Festival> festivalesVigentes = festivalRepository.findAll().stream()
                .filter(Festival::estaVigente)
                .collect(Collectors.toList());

        if (festivalesVigentes.isEmpty()) {
            throw new NoContentException("No hay festivales vigentes");
        }

        return festivalesVigentes;
    }

    public List<LocalDate> buscarFechasFestival(Long festivalId) {
        Festival festival = festivalRepository.findById(festivalId)
                .orElseThrow(() -> new EntityNotFoundException("Festival", festivalId));

        List<LocalDate> fechas = festival.getDiasFestival().stream()
                .map(DiaFestival::getFecha)
                .collect(Collectors.toList());

        if (fechas.isEmpty()) {
            throw new NoContentException("No hay fechas para el festival con ID: " + festivalId);
        }

        return fechas;
    }

    public List<SectorDisponibleDTO> buscarSectoresDisponibles(Long festivalId, LocalDate fecha) {
        Festival festival = festivalRepository.findById(festivalId)
                .orElseThrow(() -> new EntityNotFoundException("Festival", festivalId));

        return festival.buscarSectoresDisponiblesParaDias(fecha);
    }

    public List<TipoEntradaDTO> buscarTiposDeEntrada(Long festivalId, LocalDate fecha) {
        Festival festival = festivalRepository.findById(festivalId)
                .orElseThrow(() -> new EntityNotFoundException("Festival", festivalId));

        return festival.buscarTiposDeEntradaParaDias(fecha);
    }

    public VerificacionVentaAnticipadaDTO verificarVentaAnticipada(Long festivalId, LocalDateTime fechaEvento) {
        Festival festival = festivalRepository.findById(festivalId)
                .orElseThrow(() -> new EntityNotFoundException("Festival", festivalId));

        LocalDateTime fechaActual = obtenerFechaYHoraActual();
        boolean esVentaAnticipada = festival.validarFechaParaVtaAnticipada(fechaEvento, fechaActual);

        return new VerificacionVentaAnticipadaDTO(esVentaAnticipada, fechaActual);
    }

    private LocalDateTime obtenerFechaYHoraActual() {
        return LocalDateTime.now();
    }

    @Transactional
    public void bloquearButaca(BloqueoButacasRequestDTO request) {
        Festival festival = festivalRepository.findById(request.getFestivalId())
                .orElseThrow(() -> new EntityNotFoundException("Festival", request.getFestivalId()));

        Estado estadoBloqueado = estadoRepository.findByNombre("Bloqueado")
                .orElseThrow(() -> new EntityNotFoundException("Estado", "Bloqueado"));

        if (!estadoBloqueado.esBloqueada()) {
            throw new InvalidOperationException("El estado 'Bloqueado' no está configurado correctamente");
        }

        List<DisponibilidadButaca> butacasABloquear = disponibilidadButacaRepository.findAllById(request.getButacasIds());

        festival.bloquearButacaParaDias(butacasABloquear, estadoBloqueado);
    }

    public CalculoTotalResponseDTO calcularTotal(CalculoTotalRequestDTO request) {
        Festival festival = festivalRepository.findById(request.getFestivalId())
                .orElseThrow(() -> new EntityNotFoundException("Festival", request.getFestivalId()));

        Map<String, BigDecimal> subtotalesPorTipo = request.getButacasSeleccionadas().stream()
                .collect(Collectors.groupingBy(
                        ButacaSeleccionadaDTO::getTipoEntrada,
                        Collectors.reducing(
                                BigDecimal.ZERO,
                                butaca -> festival.obtenerPrecioEntradaParaDiaSectorYTipoDeEntrada(
                                        request.getFecha(),
                                        butaca.getSectorId(),
                                        butaca.getTipoEntrada()
                                ),
                                BigDecimal::add
                        )
                ));

        BigDecimal total = subtotalesPorTipo.values().stream()
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new CalculoTotalResponseDTO(subtotalesPorTipo, total);
    }
    @Transactional
    public EntradaDTO crearEntrada(CrearEntradaRequestDTO requestDTO) {
        Festival festival = festivalRepository.findById(requestDTO.getFestivalId())
                .orElseThrow(() -> new EntityNotFoundException("Festival", requestDTO.getFestivalId()));

        PuntoVenta puntoVenta = buscarPuntoVentaYCentroVta(requestDTO.getPuntoVentaId());

        DisponibilidadButaca disponibilidadButaca = disponibilidadButacaRepository.findById(requestDTO.getButacaId())
                .orElseThrow(() -> new EntityNotFoundException("DisponibilidadButaca", requestDTO.getButacaId()));

        PrecioEntrada precioEntrada = precioEntradaRepository.findById(requestDTO.getPrecioEntradaId())
                .orElseThrow(() -> new EntityNotFoundException("PrecioEntrada", requestDTO.getPrecioEntradaId()));

        Entrada nuevaEntrada = new Entrada();
        nuevaEntrada.setFechaVenta(LocalDateTime.now());
        nuevaEntrada.setMonto(requestDTO.getMonto());
        nuevaEntrada.setNumeroTicket(calcularNroEntrada());
        nuevaEntrada.setCodigoBarras(generarCodigoBarras());
        nuevaEntrada.setPuntoVenta(puntoVenta);
        nuevaEntrada.setDisponibilidadButaca(disponibilidadButaca);
        nuevaEntrada.setPrecioEntrada(precioEntrada);

        Entrada entradaGuardada = entradaRepository.save(nuevaEntrada);

        imprimirEntrada(entradaGuardada);

        actualizarDisponibilidadDeButacaPorVenta(festival, disponibilidadButaca);

        return convertirADTO(entradaGuardada);
    }

    private String calcularNroEntrada() {
        long count = entradaRepository.count();
        return String.format("%010d", count + 1);
    }

    private PuntoVenta buscarPuntoVentaYCentroVta(Long puntoVentaId) {
        return puntoVentaRepository.findById(puntoVentaId)
                .orElseThrow(() -> new EntityNotFoundException("PuntoVenta", puntoVentaId));
    }

    private String generarCodigoBarras() {
        // Implementación simple para generar un código de barras único
        return "CB" + System.currentTimeMillis();
    }

    private void imprimirEntrada(Entrada entrada) {
        PuntoVenta puntoVenta = entrada.getPuntoVenta();
        CentroDeVenta centroDeVenta = centroDeVentaRepository.findByPuntosVentaContaining(puntoVenta)
                .orElseThrow(() -> new EntityNotFoundException("CentroDeVenta para PuntoVenta", puntoVenta.getId()));

        String datosEntrada = String.format(
                "Nº Entrada: %s\nFecha y Hora: %s\nTipo Entrada: %s\nPrecio: %s\n" +
                        "Sector: %s\nFila: %s\nNº Butaca: %s\nFecha del Evento: %s\n" +
                        "Nº Punto de Venta: %s\nCentro de Venta: %s",
                entrada.getNumeroTicket(),
                entrada.getFechaVenta(),
                entrada.getPrecioEntrada().getTipoEntrada().getNombre(),
                entrada.getMonto(),
                entrada.getDisponibilidadButaca().getButaca().getFila().getSector().getLetraIdentificatoria(),
                entrada.getDisponibilidadButaca().getButaca().getFila().getNumero(),
                entrada.getDisponibilidadButaca().getButaca().getNumero(),
                entrada.getPrecioEntrada().getDiaFestival().getFecha(),
                puntoVenta.getNumero(),
                centroDeVenta.getNombre()
        );
        impresorEntrada.imprimir(datosEntrada);
    }

    private void actualizarDisponibilidadDeButacaPorVenta(Festival festival, DisponibilidadButaca disponibilidadButaca) {
        Estado estadoVendido = estadoRepository.findByNombre("Vendido")
                .orElseThrow(() -> new EntityNotFoundException("Estado", "Vendido"));

        festival.venderButacas(List.of(disponibilidadButaca), estadoVendido);
    }

    private EntradaDTO convertirADTO(Entrada entrada) {
        EntradaDTO dto = new EntradaDTO();
        dto.setId(entrada.getId());
        dto.setNumeroTicket(entrada.getNumeroTicket());
        dto.setFechaVenta(entrada.getFechaVenta());
        dto.setMonto(entrada.getMonto());
        dto.setCodigoBarras(entrada.getCodigoBarras());
        // Añadir más campos según sea necesario
        return dto;
    }




}