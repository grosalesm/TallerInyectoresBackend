package com.taller.catalogos.application.port.usecase;

import com.taller.catalogos.domain.bean.Mecanico;

import java.util.List;
import java.util.Optional;

public interface MecanicoUseCase {
    List<Mecanico> listar();
    List<Mecanico> listarActivos();
    Optional<Mecanico> obtenerPorId(Integer id);
    Mecanico guardar(Mecanico mecanico);
    void eliminar(Integer id);
}