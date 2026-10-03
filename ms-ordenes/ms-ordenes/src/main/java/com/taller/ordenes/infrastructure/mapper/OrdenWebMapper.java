package com.taller.ordenes.infrastructure.mapper;

import com.taller.ordenes.domain.bean.Orden;
import com.taller.ordenes.infrastructure.dto.OrdenRequest;
import com.taller.ordenes.infrastructure.dto.OrdenResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrdenWebMapper {
    Orden toDomain(OrdenRequest request);
    OrdenResponse toResponse(Orden orden);
}