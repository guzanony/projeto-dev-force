package br.com.DevForce.Coffe.on.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private CustomUserAdminDetailsService userAdminDetailsService;
    @Autowired
    private CustomClienteDetailsService clienteDetailsService;

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/validateToken").permitAll()
                        .requestMatchers(HttpMethod.GET, "/registerUsers").permitAll()
                        .requestMatchers(HttpMethod.POST, "/registerUsers").permitAll()
                        .requestMatchers(HttpMethod.POST, "/products").permitAll()
                        .requestMatchers(HttpMethod.GET, "/products").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/registerCliente").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/loginCliente").permitAll()
                        .requestMatchers(HttpMethod.PATCH,"/registerUsers/{id}/activate").permitAll()
                        .requestMatchers(HttpMethod.PATCH,"/registerUsers/{id}/deactivate").permitAll()
                        .requestMatchers(HttpMethod.PATCH,"/{id}/activate").permitAll()
                        .requestMatchers(HttpMethod.PATCH,"/{id}/deactivate").permitAll()
                        .requestMatchers(HttpMethod.GET, "/productsRepository/{id}").permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
