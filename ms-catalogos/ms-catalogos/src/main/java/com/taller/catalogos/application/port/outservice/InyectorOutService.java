package com.taller.catalogos.application.port.outservice;

import com.taller.catalogos.domain.bean.Inyector;

import java.util.List;
import java.util.Optional;

public interface InyectorOutService {
    List<Inyector> listar();
    List<Inyector> listarActivos();
    Optional<Inyector> obtenerPorId(Integer id);
    Inyector insertar(Inyector inyector);
    Inyector actualizar(Inyector inyector);
    void eliminar(Integer id);
}