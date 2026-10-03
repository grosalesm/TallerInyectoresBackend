package com.taller.facturacion.infrastructure.mapper;

import com.taller.facturacion.domain.bean.Recibo;
import com.taller.facturacion.infrastructure.dto.ReciboResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReciboWebMapper {
    ReciboResponse toResponse(Recibo recibo);
}