package com.taller.ordenes.application.service;

import com.taller.ordenes.application.port.outservice.CatalogoOutService;
import com.taller.ordenes.application.port.outservice.DetalleInyectorOutService;
import com.taller.ordenes.application.port.usecase.DetalleInyectorUseCase;
import com.taller.ordenes.domain.bean.DetalleInyector;
import com.taller.ordenes.domain.constraint.DetalleInyectorConstraints;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DetalleInyectorService implements DetalleInyectorUseCase {

    private final DetalleInyectorOutService detalleInyectorOutService;
    private final CatalogoOutService catalogoOutService;
    private final DetalleInyectorConstraints detalleInyectorConstraints;

    @Override
    public List<DetalleInyector> listarPorOrden(Integer idOrden) {
        List<DetalleInyector> lista = detalleInyectorOutService.listarPorOrden(idOrden);
        lista.forEach(this::enriquecerConDatosCatalogo);
        return lista;
    }

    @Override
    @Transactional
    public void agregar(Integer idOrden, DetalleInyector detalle) {
        if (!detalleInyectorConstraints.validar(detalle)) {
            throw new IllegalArgumentException("Datos del inyector inválidos.");
        }
        if (!catalogoOutService.existeInyector(detalle.getIdInyector())) {
            throw new IllegalArgumentException("El inyector no existe.");
        }
        detalle.setIdOrden(idOrden);
        detalleInyectorOutService.guardar(detalle);
    }

    @Override
    @Transactional
    public void eliminar(Integer idDetalle) {
        detalleInyectorOutService.eliminar(idDetalle);
    }

    private void enriquecerConDatosCatalogo(DetalleInyector detalle) {
        String modelo = catalogoOutService.obtenerModeloInyector(detalle.getIdInyector());
        String marca = catalogoOutService.obtenerMarcaInyector(detalle.getIdInyector());
        detalle.setModeloInyector(modelo != null ? modelo : "");
        detalle.setMarcaInyector(marca != null ? marca : "");
    }
}