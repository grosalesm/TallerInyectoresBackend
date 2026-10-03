package com.taller.catalogos.infrastructure.mapper;

import com.taller.catalogos.domain.bean.Servicio;
import com.taller.catalogos.infrastructure.persistence.entity.ServicioEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServicioMapper {
    Servicio toDomain(ServicioEntity entity);
    ServicioEntity toEntity(Servicio domain);
}