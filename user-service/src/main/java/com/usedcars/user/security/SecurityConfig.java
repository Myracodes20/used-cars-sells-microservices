package com.usedcars.user.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(JwtRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // disable csrf (we're using stateless JWT)
            .csrf(csrf -> csrf.disable())

            // explicitly disable form login so Spring doesn't intercept POSTs to unexpected URLs
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable())

            // authorize requests
            .authorizeHttpRequests(auth -> auth
                // fully allow login/register endpoints (match variants)
                .requestMatchers(
                    "/api/users/register",
                    "/api/users/register/**",
                    "/api/users/login",
                    "/api/users/login/**",
                    "/api/auth/login",
                    "/api/auth/login/**"
                ).permitAll()
                // allow actuator/health if needed (optional)
                // .requestMatchers("/actuator/health").permitAll()
                .anyRequest().authenticated()
            )

            // stateless session
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // register JWT filter before username/password filter
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
