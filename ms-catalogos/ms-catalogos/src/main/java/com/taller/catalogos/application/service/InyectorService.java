package com.taller.catalogos.application.service;

import com.taller.catalogos.application.port.outservice.CatalogoEventOutService;
import com.taller.catalogos.application.port.outservice.InyectorOutService;
import com.taller.catalogos.application.port.usecase.InyectorUseCase;
import com.taller.catalogos.domain.bean.Inyector;
import com.taller.catalogos.domain.constraint.InyectorConstraints;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InyectorService implements InyectorUseCase {

    private final InyectorOutService inyectorOutService;
    private final InyectorConstraints inyectorConstraints;
    private final CatalogoEventOutService catalogoEventOutService;

    @Override
    public List<Inyector> listar() {
        return inyectorOutService.listar();
    }

    @Override
    public List<Inyector> listarActivos() {
        return inyectorOutService.listarActivos();
    }

    @Override
    public Optional<Inyector> obtenerPorId(Integer id) {
        return inyectorOutService.obtenerPorId(id);
    }

    @Override
    @Transactional
    public Inyector guardar(Inyector inyector) {
        if (!inyectorConstraints.validar(inyector)) {
            throw new IllegalArgumentException("El modelo es obligatorio.");
        }
        boolean esNuevo = inyector.getIdInyector() == null || inyector.getIdInyector() == 0;
        if (esNuevo) {
            inyector.setIdInyector(null);
            if (inyector.getActivo() == null) inyector.setActivo(true);
            Inyector creado = inyectorOutService.insertar(inyector);
            catalogoEventOutService.publicarInyectorActualizado(creado);
            return creado;
        }
        Inyector actualizado = inyectorOutService.actualizar(inyector);
        catalogoEventOutService.publicarInyectorActualizado(actualizado);
        return actualizado;
    }

    @Override
    @Transactional
    public void eliminar(Integer id) {
        inyectorOutService.eliminar(id);
    }
}