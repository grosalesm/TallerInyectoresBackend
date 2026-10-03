package com.taller.catalogos.infrastructure.mapper;

import com.taller.catalogos.domain.bean.Inyector;
import com.taller.catalogos.infrastructure.persistence.entity.InyectorEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InyectorMapper {
    Inyector toDomain(InyectorEntity entity);
    InyectorEntity toEntity(Inyector domain);
}