package com.taller.auth.infrastructure.configuration;

import com.taller.auth.domain.enums.RolEnum;
import com.taller.auth.infrastructure.persistence.entity.UsuarioEntity;
import com.taller.auth.infrastructure.persistence.repository.UsuarioR2dbcRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UsuarioR2dbcRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        usuarioRepository.findByNombreUsuario("admin")
                .doOnNext(existente -> log.info("Usuario admin ya existe, no se crea."))
                .switchIfEmpty(crearAdmin())
                .subscribe();
    }

    private reactor.core.publisher.Mono<UsuarioEntity> crearAdmin() {
        UsuarioEntity admin = new UsuarioEntity();
        admin.setNombre("Administrador");
        admin.setNombreUsuario("admin");
        admin.setClave(passwordEncoder.encode("admin123"));
        admin.setIdRol(RolEnum.ADMINISTRADOR.getId());
        admin.setActivo(true);
        return usuarioRepository.save(admin)
                .doOnSuccess(u -> log.info("Usuario admin creado automáticamente con clave 'admin123'."));
    }
}