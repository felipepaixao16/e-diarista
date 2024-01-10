package com.project.ediarista.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.project.ediarista.core.enums.TipoUsuario;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public SecurityFilterChain weSecurityFilterChain(HttpSecurity http) throws Exception {
        http.securityMatchers(requestMatcherCustomizer ->
            requestMatcherCustomizer
                .requestMatchers("/admin/**")
        )
        .authorizeHttpRequests(authorizeRequestsCustomizer ->
            authorizeRequestsCustomizer
                .requestMatchers("/admin/resetar-senha/**").permitAll()
                .anyRequest()
                .hasAnyAuthority(TipoUsuario.ADMIN.toString())
        )
        .formLogin(formLoginCustomizer ->
            formLoginCustomizer
                .usernameParameter("email")
                .passwordParameter("senha")
                .defaultSuccessUrl("/admin/servicos")
                .permitAll()
        )
        .logout(logoutCustomizer ->
            logoutCustomizer
                .logoutRequestMatcher(new AntPathRequestMatcher("/admin/logout", "GET"))
                .logoutSuccessUrl("/admin/login")
        )
        .exceptionHandling(exceptionHandlingCustomizer ->
            exceptionHandlingCustomizer
                .accessDeniedPage("/admin/login"));

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
            .requestMatchers("/webjars/**");
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
