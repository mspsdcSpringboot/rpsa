package com.rpsa.rpsa.config;


import com.rpsa.rpsa.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractAuthenticationFilterConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
//@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig{

    @Autowired
    private JwtFilter jwtFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize ->
                                authorize
                                        .requestMatchers("/public/**", "/", "/submitAppeal", "/medilab/**", "/static/**", "/web/**", "/refresh-captcha", "/access-denied", "/userlogin", "/registration", "/register", "/resetexpiredpassword").permitAll()
                                        .requestMatchers("/secure/**").authenticated()
                                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf.disable())

                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)


                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login") // Specify the custom login page
                                .permitAll()
                )



                .logout(logout ->
                        logout
                                .logoutSuccessUrl("/") // Specify the URL to redirect after logout
                                .permitAll()

                );

        return http.build();
}

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new SHA256PasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {
        return auth.getAuthenticationManager();
    }
}
