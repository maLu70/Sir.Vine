package com.ifsp.Sir.Vine.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.ifsp.Sir.Vine.service.UsuarioDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UsuarioDetailsService usuarioDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth

                .requestMatchers("/", "/Catalogo", "/CatalogoVinho",
                        "/CatalogoQueijo", "/CatalogoEspuma",
                        "/FiltrarProdutos", "/Especificacao/**",
                        "/NovoUsuario", "/CriarUsuario",
                        "/css/**", "/js/**", "/img/**", "/icons/**",
                        "/logarUsuario", "/login")
                        .permitAll()

                .requestMatchers("/Carrinho/**").authenticated()

                .requestMatchers("/Perfil/**").authenticated()

                .requestMatchers("/CadastrarProduto", "/CriarProduto",
                                "/editarProduto/**", "/DeletarProduto/**")
                        .hasRole("ADMIN")

                .anyRequest().authenticated()
            )

            .formLogin(login -> login
                .loginPage("/login")
                .defaultSuccessUrl("/", true)
                .permitAll()
            )

            .logout(logout -> logout
                .logoutSuccessUrl("/")
                .permitAll()
            );
        
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(usuarioDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }
}
