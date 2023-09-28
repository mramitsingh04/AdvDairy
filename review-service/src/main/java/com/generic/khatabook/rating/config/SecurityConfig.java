package com.generic.khatabook.rating.config;
/*

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public RoleHierarchy roleHierarchy(){
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();
        hierarchy.setHierarchy("""
                 ROLE_ADMIN > ROLE_STAFF
                 ROLE_STAFF > ROLE_USER
                 ROLE_USER > ROLE_GUEST""");
        return hierarchy;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http

                .authorizeHttpRequests(auth -> auth.requestMatchers(antMatcher("/hello-world/**"),
                        antMatcher("/hello/**"),
                        antMatcher("/actuator/**")
                ).permitAll())


                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers(antMatcher("/rating-service/**"))
                        .hasAnyRole(Role.USER.name(), Role.ADMIN.name()))



                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.GET, "/comment-service/**").hasRole(Role.USER.name())
                        .requestMatchers(HttpMethod.POST, "/comment-service/**").hasRole(Role.USER.name())
                        .requestMatchers(HttpMethod.PUT, "/comment-service/**").hasRole(Role.ADMIN.name()))

                .httpBasic(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .headers(AbstractHttpConfigurer::disable)
                .userDetailsService(userDetailsManager())
                .build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("user").password(passwordEncoder().encode("123456")).roles(Role.USER.name()).build());
        manager.createUser(User.withUsername("amit").password(passwordEncoder().encode("123456")).roles(Role.STAFF.name()).build());
        manager.createUser(User.withUsername("admin").password(passwordEncoder().encode("123456")).roles(Role.ADMIN.name()).build());
        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

}
*/
