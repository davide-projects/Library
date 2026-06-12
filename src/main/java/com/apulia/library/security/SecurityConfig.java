package com.apulia.library.security;

import com.apulia.library.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final CustomAuthEntryPoint customAuthEntryPoint;
    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(CustomAuthEntryPoint customAuthEntryPoint,
                          UserDetailsServiceImpl userDetailsService) {
        this.customAuthEntryPoint = customAuthEntryPoint;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)

                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(customAuthEntryPoint)
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            ErrorResponse error = new ErrorResponse(
                                    HttpStatus.FORBIDDEN.value(),
                                    "FORBIDDEN",
                                    "You do not have permission to access this resource"
                            );
                            response.setStatus(HttpStatus.FORBIDDEN.value());
                            response.setContentType("application/json");
                            new ObjectMapper().writeValue(response.getOutputStream(), error);
                        })
                )

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/favicon.ico",
                                "/login.html",
                                "/components/**",
                                "/index.html",
                                "/books.html",
                                "/members.html",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/webjars/**"
                        ).permitAll()
                        .requestMatchers("/swagger-ui.html").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/v3/api-docs").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/auth/register").permitAll()

                                // 👤 MEMBERS
                                .requestMatchers(HttpMethod.GET, "/members", "/members/**").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.POST, "/members", "/members/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/members", "/members/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PATCH, "/members", "/members/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/members", "/members/**").hasRole("ADMIN")

                                // 📚 BOOKS
                                .requestMatchers(HttpMethod.GET, "/book", "/book/**").hasAnyRole("USER", "ADMIN")
                                .requestMatchers(HttpMethod.POST, "/book", "/book/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/book", "/book/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PATCH, "/book", "/book/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/book", "/book/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )

                .httpBasic(basic -> basic.realmName("Library API"))
                .userDetailsService(userDetailsService);

        return http.build();
    }
}