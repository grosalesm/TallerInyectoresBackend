package com.taller.clientes.infrastructure.mapper;

import com.taller.clientes.domain.bean.Cliente;
import com.taller.clientes.infrastructure.dto.ClienteRequest;
import com.taller.clientes.infrastructure.dto.ClienteResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClienteWebMapper {
    Cliente toDomain(ClienteRequest request);
    ClienteResponse toResponse(Cliente cliente);
}