package com.taller.auth.application.port.outservice;

public interface PasswordEncoderOutService {
    String encode(String rawPassword);
    boolean matches(String rawPassword, String encodedPassword);
}