package com.taller.ordenes.application.service;

import com.taller.ordenes.application.port.outservice.CatalogoOutService;
import com.taller.ordenes.application.port.outservice.DetalleServicioOutService;
import com.taller.ordenes.application.port.outservice.OrdenOutService;
import com.taller.ordenes.application.port.usecase.DetalleServicioUseCase;
import com.taller.ordenes.domain.bean.DetalleServicio;
import com.taller.ordenes.domain.constraint.DetalleServicioConstraints;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DetalleServicioService implements DetalleServicioUseCase {

    private final DetalleServicioOutService detalleServicioOutService;
    private final OrdenOutService ordenOutService;
    private final CatalogoOutService catalogoOutService;
    private final DetalleServicioConstraints detalleServicioConstraints;

    @Override
    public List<DetalleServicio> listarPorOrden(Integer idOrden) {
        List<DetalleServicio> lista = detalleServicioOutService.listarPorOrden(idOrden);
        lista.forEach(this::enriquecerConDatosCatalogo);
        return lista;
    }

    @Override
    @Transactional
    public void agregar(Integer idOrden, DetalleServicio detalle) {
        if (!detalleServicioConstraints.validar(detalle)) {
            throw new IllegalArgumentException("Datos del servicio inválidos.");
        }
        if (!catalogoOutService.existeServicio(detalle.getIdServicio())) {
            throw new IllegalArgumentException("El servicio no existe.");
        }
        detalle.setIdOrden(idOrden);
        detalleServicioOutService.guardar(detalle);
        recalcularTotal(idOrden);
    }

    @Override
    @Transactional
    public void eliminar(Integer idDetalle) {
        DetalleServicio detalle = detalleServicioOutService.obtenerPorId(idDetalle)
                .orElseThrow(() -> new IllegalArgumentException("Detalle no encontrado."));
        Integer idOrden = detalle.getIdOrden();
        detalleServicioOutService.eliminar(idDetalle);
        recalcularTotal(idOrden);
    }

    private void enriquecerConDatosCatalogo(DetalleServicio detalle) {
        String nombre = catalogoOutService.obtenerNombreServicio(detalle.getIdServicio());
        detalle.setNombreServicio(nombre != null ? nombre : "");
    }

    private void recalcularTotal(Integer idOrden) {
        Double total = ordenOutService.calcularTotalPorOrden(idOrden);
        ordenOutService.actualizarTotal(idOrden, total);
    }
}