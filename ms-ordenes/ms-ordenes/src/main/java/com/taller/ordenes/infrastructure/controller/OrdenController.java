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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public List<OrdenResponse> listar() {
        return ordenUseCase.listar().stream().map(ordenWebMapper::toResponse).toList();
    }

    @GetMapping("/estado/{estado}")
    public List<OrdenResponse> listarPorEstado(@PathVariable String estado) {
        return ordenUseCase.listarPorEstado(estado).stream().map(ordenWebMapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrdenResponse> obtenerPorId(@PathVariable Integer id) {
        return ordenUseCase.obtenerPorId(id)
                .map(orden -> {
                    OrdenResponse response = ordenWebMapper.toResponse(orden);
                    response.setInyectores(detalleInyectorMapper.toResponseList(
                            detalleInyectorUseCase.listarPorOrden(id)));
                    response.setServicios(detalleServicioMapper.toResponseList(
                            detalleServicioUseCase.listarPorOrden(id)));
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/inyectores")
    public List<DetalleInyector> listarInyectores(@PathVariable Integer id) {
        return detalleInyectorUseCase.listarPorOrden(id);
    }

    @GetMapping("/{id}/servicios")
    public List<DetalleServicio> listarServicios(@PathVariable Integer id) {
        return detalleServicioUseCase.listarPorOrden(id);
    }

    @PostMapping
    public ResponseEntity<OrdenResponse> crear(@Valid @RequestBody OrdenRequest request) {
        Orden orden = ordenWebMapper.toDomain(request);
        orden.setIdOrden(null);
        Orden creada = ordenUseCase.crear(orden);
        return ResponseEntity.ok(ordenWebMapper.toResponse(creada));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<RespuestaMensaje> cambiarEstado(@PathVariable Integer id,
                                                          @Valid @RequestBody PeticionEstado request) {
        ordenUseCase.cambiarEstado(id, request.getEstado());
        return ResponseEntity.ok(new RespuestaMensaje("Estado actualizado a '" + request.getEstado() + "'."));
    }

    @PostMapping("/{id}/inyectores")
    public ResponseEntity<RespuestaMensaje> agregarInyector(@PathVariable Integer id,
                                                            @Valid @RequestBody DetalleInyectorRequest request) {
        DetalleInyector detalle = new DetalleInyector();
        detalle.setIdInyector(request.getIdInyector());
        detalle.setCantidad(request.getCantidad());
        detalleInyectorUseCase.agregar(id, detalle);
        return ResponseEntity.ok(new RespuestaMensaje("Inyector agregado."));
    }

    @PostMapping("/{id}/servicios")
    public ResponseEntity<RespuestaMensaje> agregarServicio(@PathVariable Integer id,
                                                            @Valid @RequestBody DetalleServicioRequest request) {
        DetalleServicio detalle = new DetalleServicio();
        detalle.setIdServicio(request.getIdServicio());
        detalle.setCantidad(request.getCantidad());
        detalle.setPrecioUnitario(request.getPrecioUnitario());
        detalleServicioUseCase.agregar(id, detalle);
        return ResponseEntity.ok(new RespuestaMensaje("Servicio agregado."));
    }

    @DeleteMapping("/inyectores/{idDetalle}")
    public ResponseEntity<Void> eliminarInyector(@PathVariable Integer idDetalle) {
        detalleInyectorUseCase.eliminar(idDetalle);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/servicios/{idDetalle}")
    public ResponseEntity<Void> eliminarServicio(@PathVariable Integer idDetalle) {
        detalleServicioUseCase.eliminar(idDetalle);
        return ResponseEntity.noContent().build();
    }
}