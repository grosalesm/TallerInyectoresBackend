package com.taller.catalogos.infrastructure.mapper;

import com.taller.catalogos.domain.bean.Mecanico;
import com.taller.catalogos.infrastructure.persistence.entity.MecanicoEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MecanicoMapper {
    Mecanico toDomain(MecanicoEntity entity);
    MecanicoEntity toEntity(Mecanico domain);
}