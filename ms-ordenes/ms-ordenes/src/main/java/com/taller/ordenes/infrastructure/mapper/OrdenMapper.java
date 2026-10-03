package com.taller.ordenes.infrastructure.mapper;

import com.taller.ordenes.domain.bean.Orden;
import com.taller.ordenes.infrastructure.persistence.entity.OrdenEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrdenMapper {
    Orden toDomain(OrdenEntity entity);
    OrdenEntity toEntity(Orden domain);
}