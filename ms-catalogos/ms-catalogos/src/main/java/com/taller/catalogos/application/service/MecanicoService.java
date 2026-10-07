package com.taller.catalogos.application.service;

import com.taller.catalogos.application.port.outservice.CatalogoEventOutService;
import com.taller.catalogos.application.port.outservice.MecanicoOutService;
import com.taller.catalogos.application.port.usecase.MecanicoUseCase;
import com.taller.catalogos.domain.bean.Mecanico;
import com.taller.catalogos.domain.constraint.MecanicoConstraints;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MecanicoService implements MecanicoUseCase {

    private final MecanicoOutService mecanicoOutService;
    private final MecanicoConstraints mecanicoConstraints;
    private final CatalogoEventOutService catalogoEventOutService;

    @Override
    public List<Mecanico> listar() {
        return mecanicoOutService.listar();
    }

    @Override
    public List<Mecanico> listarActivos() {
        return mecanicoOutService.listarActivos();
    }

    @Override
    public Optional<Mecanico> obtenerPorId(Integer id) {
        return mecanicoOutService.obtenerPorId(id);
    }

    @Override
    @Transactional
    public Mecanico guardar(Mecanico mecanico) {
        if (!mecanicoConstraints.validar(mecanico)) {
            throw new IllegalArgumentException("El nombre y el apellido son obligatorios.");
        }
        boolean esNuevo = mecanico.getIdMecanico() == null || mecanico.getIdMecanico() == 0;
        if (esNuevo) {
            mecanico.setIdMecanico(null);
            if (mecanico.getActivo() == null) mecanico.setActivo(true);
            Mecanico creado = mecanicoOutService.insertar(mecanico);
            catalogoEventOutService.publicarMecanicoActualizado(creado);
            return creado;
        }
        Mecanico actualizado = mecanicoOutService.actualizar(mecanico);
        catalogoEventOutService.publicarMecanicoActualizado(actualizado);
        return actualizado;
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        mecanicoOutService.eliminar(id);
    }
}