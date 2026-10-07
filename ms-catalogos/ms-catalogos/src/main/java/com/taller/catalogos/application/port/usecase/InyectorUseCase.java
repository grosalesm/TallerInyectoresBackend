package com.taller.catalogos.application.port.usecase;

import com.taller.catalogos.domain.bean.Inyector;

import java.util.List;
import java.util.Optional;

public interface InyectorUseCase {
    List<Inyector> listar();
    List<Inyector> listarActivos();
    Optional<Inyector> obtenerPorId(Integer id);
    Inyector guardar(Inyector inyector);
    void eliminar(Integer id);
}