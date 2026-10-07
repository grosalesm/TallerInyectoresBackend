package com.taller.auth.infrastructure.configuration;

import com.taller.auth.domain.enums.RolEnum;
import com.taller.auth.infrastructure.persistence.entity.UsuarioEntity;
import com.taller.auth.infrastructure.persistence.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (usuarioRepository.findByNombreUsuario("admin").isPresent()) {
            log.info("Usuario admin ya existe, no se crea.");
            return;
        }

        UsuarioEntity admin = new UsuarioEntity();
        admin.setNombre("Administrador");
        admin.setNombreUsuario("admin");
        admin.setClave(passwordEncoder.encode("admin123"));
        admin.setIdRol(RolEnum.ADMINISTRADOR.getId());
        admin.setActivo(true);
        usuarioRepository.save(admin);
        log.info("Usuario admin creado automáticamente con clave 'admin123'.");
    }
}