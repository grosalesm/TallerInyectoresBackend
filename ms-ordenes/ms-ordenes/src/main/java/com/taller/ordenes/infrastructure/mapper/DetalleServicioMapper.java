package com.taller.ordenes.infrastructure.mapper;

import com.taller.ordenes.domain.bean.DetalleServicio;
import com.taller.ordenes.infrastructure.dto.DetalleServicioResponse;
import com.taller.ordenes.infrastructure.persistence.entity.DetalleServicioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DetalleServicioMapper {

    DetalleServicio toDomain(DetalleServicioEntity entity);
    DetalleServicioEntity toEntity(DetalleServicio domain);

    @Mapping(target = "subtotal", expression = "java(domain.getSubtotal())")
    DetalleServicioResponse toResponse(DetalleServicio domain);

    List<DetalleServicioResponse> toResponseList(List<DetalleServicio> domain);
}