// src/main/java/com/example/myfirstapplication/config/SecurityConfig.java
package com.example.myfirstapplication.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; // Importar BCryptPasswordEncoder

@Configuration // Indica que esta clase contiene definiciones de beans de Spring
@EnableWebSecurity // Habilita la seguridad web de Spring
public class SecurityConfig {

    // Define un bean para el BCryptPasswordEncoder.
    // Spring lo inyectará automáticamente donde se necesite (ej. en UserService)
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configura la cadena de filtros de seguridad HTTP
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Deshabilita CSRF para APIs REST (¡cuidado en producción con frontends web!)
            .authorizeHttpRequests(authorize -> authorize
                // Permite el acceso sin autenticación a la ruta de registro
                .anyRequest().permitAll()
            )
            .httpBasic(httpBasic -> {}); // Habilita la autenticación HTTP Basic (opcional si no la usarás)
                                        // O puedes quitar esta línea si no quieres ninguna autenticación predeterminada
                                        // Si la dejas, puedes ver un popup de credenciales en el navegador para otras rutas.

        return http.build();
    }
}
