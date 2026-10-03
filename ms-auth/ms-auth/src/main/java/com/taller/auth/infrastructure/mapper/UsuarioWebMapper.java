package com.taller.auth.infrastructure.mapper;

import com.taller.auth.domain.bean.Usuario;
import com.taller.auth.infrastructure.dto.UsuarioResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioWebMapper {
    UsuarioResponse toResponse(Usuario usuario);
}