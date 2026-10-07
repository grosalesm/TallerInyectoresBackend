package com.taller.catalogos.application.service;

import com.taller.catalogos.application.port.outservice.CatalogoEventOutService;
import com.taller.catalogos.application.port.outservice.ServicioOutService;
import com.taller.catalogos.application.port.usecase.ServicioUseCase;
import com.taller.catalogos.domain.bean.Servicio;
import com.taller.catalogos.domain.constraint.ServicioConstraints;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServicioService implements ServicioUseCase {

    private final ServicioOutService servicioOutService;
    private final ServicioConstraints servicioConstraints;
    private final CatalogoEventOutService catalogoEventOutService;

    @Override
    public List<Servicio> listar() {
        return servicioOutService.listar();
    }

    @Override
    public List<Servicio> listarActivos() {
        return servicioOutService.listarActivos();
    }

    @Override
    public Optional<Servicio> obtenerPorId(Integer id) {
        return servicioOutService.obtenerPorId(id);
    }

    @Override
    @Transactional
    public Servicio guardar(Servicio servicio) {
        if (!servicioConstraints.validar(servicio)) {
            throw new IllegalArgumentException("El nombre es obligatorio y el precio debe ser mayor a cero.");
        }
        boolean esNuevo = servicio.getIdServicio() == null || servicio.getIdServicio() == 0;
        if (esNuevo) {
            servicio.setIdServicio(null);
            if (servicio.getActivo() == null) servicio.setActivo(true);
            Servicio creado = servicioOutService.insertar(servicio);
            catalogoEventOutService.publicarServicioActualizado(creado);
            return creado;
        }
        Servicio actualizado = servicioOutService.actualizar(servicio);
        catalogoEventOutService.publicarServicioActualizado(actualizado);
        return actualizado;
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        servicioOutService.eliminar(id);
    }
}