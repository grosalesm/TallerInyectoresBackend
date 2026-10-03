package com.taller.auth.application.port.outservice;

import com.taller.auth.domain.bean.Usuario;

public interface JwtOutService {
    String generarToken(Usuario usuario);
}