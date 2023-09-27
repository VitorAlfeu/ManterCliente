package com.teste.mantercliente.api.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Configuração de segurança da aplicação, que inclui configurações de autenticação e autorização.
 */
@Configuration
public class WebSecurityConfig {

    /**
     * Define um bean para o codificador de senhas utilizado na aplicação.
     *
     * @return Um objeto PasswordEncoder para codificar senhas.
     */
    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * Define um bean para o serviço de detalhes do usuário em memória, que fornece detalhes de um usuário
     * com nome de usuário "admin" e senha codificada com o codificador definido.
     *
     * @return Um InMemoryUserDetailsManager configurado com um usuário "admin".
     */
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user1 = User.builder()
                .username("admin")
                .password(encoder().encode("admin")) // Senha codificada
                .roles("MAIN")
                .build();
        return new InMemoryUserDetailsManager(user1);
    }

    /**
     * Define uma cadeia de filtros de segurança HTTP que configura as regras de autorização e autenticação.
     *
     * @param http O objeto HttpSecurity que permite a configuração das regras de segurança.
     * @return Uma cadeia de filtros de segurança configurada.
     * @throws Exception Exceção lançada em caso de erro na configuração.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((authz) -> authz
                .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll())
            .csrf(AbstractHttpConfigurer::disable)
            .headers((header) -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
            .authorizeHttpRequests((authz) -> authz
                .requestMatchers(AntPathRequestMatcher.antMatcher("/api/clientes/**")).hasRole("MAIN")
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults());
        return http.build();
    }
}
