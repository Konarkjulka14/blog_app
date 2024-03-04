package com.springbootblog.config;

import com.springbootblog.security.JwtAuthenticationEntryPoint;
import com.springbootblog.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private UserDetailsService userDetailsService;
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(UserDetailsService userDetailsService,
                          JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                          JwtAuthenticationFilter jwtAuthenticationFilter)
    {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(csrf ->csrf.disable());
        http.authorizeHttpRequests((authorize) ->
                authorize
                        .requestMatchers(HttpMethod.GET,"api/posts").hasRole("USER")
                        .requestMatchers(HttpMethod.GET,"api/posts/**").hasRole("USER")
                        .requestMatchers(HttpMethod.POST,"api/posts").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"api/auth/**").permitAll()

        ).exceptionHandling(exception ->
                exception.authenticationEntryPoint(jwtAuthenticationEntryPoint)
        ).sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }



//     @Bean
//    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//        http.authorizeHttpRequests((authorize) ->
//                authorize
//                        .requestMatchers(HttpMethod.GET,"api/posts").permitAll()
//                        .anyRequest().authenticated()
//        );
//
//        http.httpBasic(Customizer.withDefaults());
//        http.csrf(csrf ->csrf.disable());
//
//        return http.build();
//    }

//    @Bean
//    public UserDetailsService userDetailsService()
//    {
//        UserDetails konark = User.builder()
//                .username("konark")
//                .password(passwordEncoder().encode("konark"))
//                .roles("USER").build();
//
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN").build();
//
//        return new InMemoryUserDetailsManager(konark, admin);
//    }
}
