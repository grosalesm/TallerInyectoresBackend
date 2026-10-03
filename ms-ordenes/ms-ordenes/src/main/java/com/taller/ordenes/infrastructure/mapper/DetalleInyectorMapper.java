package com.taller.ordenes.infrastructure.mapper;

import com.taller.ordenes.domain.bean.DetalleInyector;
import com.taller.ordenes.infrastructure.dto.DetalleInyectorResponse;
import com.taller.ordenes.infrastructure.persistence.entity.DetalleInyectorEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DetalleInyectorMapper {
    DetalleInyector toDomain(DetalleInyectorEntity entity);
    DetalleInyectorEntity toEntity(DetalleInyector domain);

    DetalleInyectorResponse toResponse(DetalleInyector domain);
    List<DetalleInyectorResponse> toResponseList(List<DetalleInyector> domain);
}