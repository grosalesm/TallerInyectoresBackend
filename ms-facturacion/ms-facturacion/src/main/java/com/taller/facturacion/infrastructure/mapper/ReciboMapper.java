package com.taller.facturacion.infrastructure.mapper;

import com.taller.facturacion.domain.bean.Recibo;
import com.taller.facturacion.infrastructure.persistence.entity.ReciboEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReciboMapper {
    Recibo toDomain(ReciboEntity entity);
    ReciboEntity toEntity(Recibo domain);
}