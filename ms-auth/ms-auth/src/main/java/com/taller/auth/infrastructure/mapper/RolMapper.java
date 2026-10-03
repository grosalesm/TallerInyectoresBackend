package com.taller.auth.infrastructure.mapper;

import com.taller.auth.domain.bean.Rol;
import com.taller.auth.infrastructure.persistence.entity.RolEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RolMapper {
    Rol toDomain(RolEntity entity);
}