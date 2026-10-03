package com.taller.ordenes.infrastructure.controller;

import com.taller.ordenes.application.port.usecase.DetalleInyectorUseCase;
import com.taller.ordenes.application.port.usecase.DetalleServicioUseCase;
import com.taller.ordenes.application.port.usecase.OrdenUseCase;
import com.taller.ordenes.domain.bean.DetalleInyector;
import com.taller.ordenes.domain.bean.DetalleServicio;
import com.taller.ordenes.domain.bean.Orden;
import com.taller.ordenes.infrastructure.dto.DetalleInyectorRequest;
import com.taller.ordenes.infrastructure.dto.DetalleServicioRequest;
import com.taller.ordenes.infrastructure.dto.OrdenRequest;
import com.taller.ordenes.infrastructure.dto.OrdenResponse;
import com.taller.ordenes.infrastructure.dto.PeticionEstado;
import com.taller.ordenes.infrastructure.dto.RespuestaMensaje;
import com.taller.ordenes.infrastructure.mapper.DetalleInyectorMapper;
import com.taller.ordenes.infrastructure.mapper.DetalleServicioMapper;
import com.taller.ordenes.infrastructure.mapper.OrdenWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/orden")
@RequiredArgsConstructor
public class OrdenController {

    private final OrdenUseCase ordenUseCase;
    private final DetalleInyectorUseCase detalleInyectorUseCase;
    private final DetalleServicioUseCase detalleServicioUseCase;
    private final OrdenWebMapper ordenWebMapper;
    private final DetalleInyectorMapper detalleInyectorMapper;
    private final DetalleServicioMapper detalleServicioMapper;

    @GetMapping
    public Flux<OrdenResponse> listar() {
        return ordenUseCase.listar().map(ordenWebMapper::toResponse);
    }

    @GetMapping("/estado/{estado}")
    public Flux<OrdenResponse> listarPorEstado(@PathVariable String estado) {
        return ordenUseCase.listarPorEstado(estado).map(ordenWebMapper::toResponse);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<OrdenResponse>> obtenerPorId(@PathVariable Integer id) {
        return ordenUseCase.obtenerPorId(id)
                .flatMap(orden -> Mono.zip(
                        detalleInyectorUseCase.listarPorOrden(id).collectList(),
                        detalleServicioUseCase.listarPorOrden(id).collectList()
                ).map(tuple -> {
                    OrdenResponse response = ordenWebMapper.toResponse(orden);
                    response.setInyectores(detalleInyectorMapper.toResponseList(tuple.getT1()));
                    response.setServicios(detalleServicioMapper.toResponseList(tuple.getT2()));
                    return ResponseEntity.ok(response);
                }))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/inyectores")
    public Flux<DetalleInyector> listarInyectores(@PathVariable Integer id) {
        return detalleInyectorUseCase.listarPorOrden(id);
    }

    @GetMapping("/{id}/servicios")
    public Flux<DetalleServicio> listarServicios(@PathVariable Integer id) {
        return detalleServicioUseCase.listarPorOrden(id);
    }

    @PostMapping
    public Mono<ResponseEntity<OrdenResponse>> crear(@Valid @RequestBody OrdenRequest request) {
        Orden orden = ordenWebMapper.toDomain(request);
        orden.setIdOrden(0);
        return ordenUseCase.crear(orden)
                .map(ordenWebMapper::toResponse)
                .map(ResponseEntity::ok);
    }

    @PatchMapping("/{id}/estado")
    public Mono<ResponseEntity<RespuestaMensaje>> cambiarEstado(@PathVariable Integer id,
                                                                @Valid @RequestBody PeticionEstado request) {
        return ordenUseCase.cambiarEstado(id, request.getEstado())
                .thenReturn(ResponseEntity.ok(
                        new RespuestaMensaje("Estado actualizado a '" + request.getEstado() + "'.")));
    }

    @PostMapping("/{id}/inyectores")
    public Mono<ResponseEntity<RespuestaMensaje>> agregarInyector(@PathVariable Integer id,
                                                                  @Valid @RequestBody DetalleInyectorRequest request) {
        DetalleInyector detalle = new DetalleInyector();
        detalle.setIdInyector(request.getIdInyector());
        detalle.setCantidad(request.getCantidad());
        return detalleInyectorUseCase.agregar(id, detalle)
                .thenReturn(ResponseEntity.ok(new RespuestaMensaje("Inyector agregado.")));
    }

    @PostMapping("/{id}/servicios")
    public Mono<ResponseEntity<RespuestaMensaje>> agregarServicio(@PathVariable Integer id,
                                                                  @Valid @RequestBody DetalleServicioRequest request) {
        DetalleServicio detalle = new DetalleServicio();
        detalle.setIdServicio(request.getIdServicio());
        detalle.setCantidad(request.getCantidad());
        detalle.setPrecioUnitario(request.getPrecioUnitario());
        return detalleServicioUseCase.agregar(id, detalle)
                .thenReturn(ResponseEntity.ok(new RespuestaMensaje("Servicio agregado.")));
    }

    @DeleteMapping("/inyectores/{idDetalle}")
    public Mono<ResponseEntity<Void>> eliminarInyector(@PathVariable Integer idDetalle) {
        return detalleInyectorUseCase.eliminar(idDetalle)
                .thenReturn(ResponseEntity.noContent().build());
    }

    @DeleteMapping("/servicios/{idDetalle}")
    public Mono<ResponseEntity<Void>> eliminarServicio(@PathVariable Integer idDetalle) {
        return detalleServicioUseCase.eliminar(idDetalle)
                .thenReturn(ResponseEntity.noContent().build());
    }
}