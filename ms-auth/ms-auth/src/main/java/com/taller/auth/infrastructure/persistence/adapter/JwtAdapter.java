package com.taller.auth.infrastructure.persistence.adapter;

import com.taller.auth.application.port.outservice.JwtOutService;
import com.taller.auth.domain.bean.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtAdapter implements JwtOutService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-days}")
    private int expirationDays;

    @Override
    public String generarToken(Usuario usuario) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .subject(usuario.getIdUsuario().toString())
                .claim("nombre", usuario.getNombre())
                .claim("rol", usuario.getNombreRol())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + (long) expirationDays * 24 * 60 * 60 * 1000))
                .signWith(key, Jwts.SIG.HS256)
                .compact();
    }
}