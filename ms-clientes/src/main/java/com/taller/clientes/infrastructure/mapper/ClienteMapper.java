package com.taller.clientes.infrastructure.mapper;

import com.taller.clientes.domain.bean.Cliente;
import com.taller.clientes.infrastructure.persistence.entity.ClienteEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClienteMapper {
    Cliente toDomain(ClienteEntity entity);
    ClienteEntity toEntity(Cliente domain);
}