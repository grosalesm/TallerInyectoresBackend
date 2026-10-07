package com.taller.ordenes.application.service;

import com.taller.ordenes.application.port.outservice.ClienteOutService;
import com.taller.ordenes.application.port.outservice.MecanicoOutService;
import com.taller.ordenes.application.port.outservice.OrdenEventOutService;
import com.taller.ordenes.application.port.outservice.OrdenOutService;
import com.taller.ordenes.application.port.usecase.OrdenUseCase;
import com.taller.ordenes.domain.bean.Orden;
import com.taller.ordenes.domain.constraint.OrdenConstraints;
import com.taller.ordenes.domain.enums.EstadoOrden;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrdenService implements OrdenUseCase {

    private final OrdenOutService ordenOutService;
    private final ClienteOutService clienteOutService;
    private final MecanicoOutService mecanicoOutService;
    private final OrdenEventOutService ordenEventOutService;
    private final OrdenConstraints ordenConstraints;

    @Override
    public List<Orden> listar() {
        List<Orden> lista = ordenOutService.listar();
        lista.forEach(this::enriquecerConNombres);
        return lista;
    }

    @Override
    public List<Orden> listarPorEstado(String estado) {
        List<Orden> lista = ordenOutService.listarPorEstado(estado);
        lista.forEach(this::enriquecerConNombres);
        return lista;
    }

    @Override
    public Optional<Orden> obtenerPorId(Integer id) {
        return ordenOutService.obtenerPorId(id).map(this::enriquecerConNombres);
    }

    @Override
    @Transactional
    public Orden crear(Orden orden) {
        if (!ordenConstraints.validarCreacion(orden)) {
            throw new IllegalArgumentException("Debe seleccionar un cliente y un mecánico válidos.");
        }
        if (!clienteOutService.existeCliente(orden.getIdCliente())) {
            throw new IllegalArgumentException("El cliente no existe.");
        }
        if (!mecanicoOutService.existeMecanico(orden.getIdMecanico())) {
            throw new IllegalArgumentException("El mecánico no existe.");
        }
        orden.setEstado(EstadoOrden.PENDIENTE.getDescripcion());
        Orden creada = ordenOutService.insertar(orden);
        ordenEventOutService.publicarOrdenCreada(creada);
        return enriquecerConNombres(creada);
    }

    @Override
    @Transactional
    public void cambiarEstado(Integer idOrden, String estado) {
        if (!ordenConstraints.validarEstadoManual(estado)) {
            throw new IllegalArgumentException(
                    "Estado no válido. Solo se permite 'Pendiente', 'En Proceso' o 'Terminado' manualmente.");
        }
        Orden orden = ordenOutService.obtenerPorId(idOrden)
                .orElseThrow(() -> new IllegalArgumentException("Orden no encontrada."));
        String anterior = orden.getEstado();
        ordenOutService.cambiarEstado(idOrden, estado);
        ordenEventOutService.publicarEstadoCambiado(idOrden, anterior, estado);
    }

    @Override
    @Transactional
    public void cambiarEstadoPorEvento(Integer idOrden, String estado) {
        if (!ordenConstraints.validarEstadoPorEvento(estado)) {
            throw new IllegalArgumentException("Estado no válido.");
        }
        Orden orden = ordenOutService.obtenerPorId(idOrden)
                .orElseThrow(() -> new IllegalArgumentException("Orden no encontrada."));
        String anterior = orden.getEstado();
        if (anterior != null && anterior.equalsIgnoreCase(estado)) {
            return;
        }
        ordenOutService.cambiarEstado(idOrden, estado);
        ordenEventOutService.publicarEstadoCambiado(idOrden, anterior, estado);
    }

    private Orden enriquecerConNombres(Orden orden) {
        String nombreCliente = clienteOutService.obtenerNombreCliente(orden.getIdCliente());
        String nombreMecanico = mecanicoOutService.obtenerNombreMecanico(orden.getIdMecanico());
        orden.setNombreCliente(nombreCliente != null ? nombreCliente : "");
        orden.setNombreMecanico(nombreMecanico != null ? nombreMecanico : "");
        return orden;
    }
}