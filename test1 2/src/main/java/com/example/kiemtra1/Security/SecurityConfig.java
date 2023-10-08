package com.example.kiemtra1.Security;

import com.example.kiemtra1.Model.Role;
import com.example.kiemtra1.Service.OrderSevice;
import com.example.kiemtra1.Token.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Autowired
    private JwtAuthenticationFilter jwt;
    public final AuthenticationProvider authenticationProvider;
    private final static Logger LOGGER = LoggerFactory
            .getLogger(SecurityConfig.class);

    @Bean
    public OAuth2AuthorizedClientService authorizedClientService(ClientRegistrationRepository clientRegistrationRepository) {
        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository);
    }
    @Bean
    public SecurityFilterChain googleOAuth2SecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf-> csrf.disable())
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .oauth2Login(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    @Order(0)
    public SecurityFilterChain SecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());
        http.authenticationProvider(authenticationProvider);
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authorizeHttpRequests(auth -> {
                    auth.anyRequest().permitAll();
                }
        );
        http.addFilterBefore(jwt, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


        //　あしたまたチェクしてください
        /*
         * check security try agin*/

}


