package com.taller.auth.infrastructure.mapper;

import com.taller.auth.domain.bean.Usuario;
import com.taller.auth.infrastructure.persistence.entity.UsuarioEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    Usuario toDomain(UsuarioEntity entity);
    UsuarioEntity toEntity(Usuario domain);
}