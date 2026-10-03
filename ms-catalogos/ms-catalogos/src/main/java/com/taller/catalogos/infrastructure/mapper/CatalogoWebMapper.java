package com.taller.catalogos.infrastructure.mapper;

import com.taller.catalogos.domain.bean.Inyector;
import com.taller.catalogos.domain.bean.Mecanico;
import com.taller.catalogos.domain.bean.Servicio;
import com.taller.catalogos.infrastructure.dto.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CatalogoWebMapper {
    Inyector toDomain(InyectorRequest request);
    InyectorResponse toResponse(Inyector inyector);

    Servicio toDomain(ServicioRequest request);
    ServicioResponse toResponse(Servicio servicio);

    Mecanico toDomain(MecanicoRequest request);
    MecanicoResponse toResponse(Mecanico mecanico);
}