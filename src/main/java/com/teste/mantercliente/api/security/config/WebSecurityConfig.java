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

@Configuration
public class WebSecurityConfig {

	@Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user1 = User.builder()
                .username("admin")
                .password(encoder().encode("admin"))
                .roles("MAIN")
                .build();
        return new InMemoryUserDetailsManager(user1);
    }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
            		.authorizeHttpRequests((authz) -> authz
            				.requestMatchers(AntPathRequestMatcher.antMatcher("/h2-coinsole/**")).permitAll())
                    .csrf(AbstractHttpConfigurer::disable).headers((header) -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                    .authorizeHttpRequests((authz) -> authz
                            .requestMatchers(AntPathRequestMatcher.antMatcher("/api/clientes/**")).hasRole("MAIN")
                            .anyRequest().authenticated()
                    )
                    .httpBasic(Customizer.withDefaults());
            return http.build();
    }
	
}
