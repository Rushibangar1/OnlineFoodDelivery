package com.FoodGuy.Config;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class AppConfig {



  @Bean
  DefaultSecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
     return 
             http.sessionManagement(management-> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
              .authorizeHttpRequests(Authorize -> Authorize
                      .requestMatchers("/test").permitAll()
                      .requestMatchers("/api/admin/**").hasAnyRole("Role_Restaurant_Owner","ADMIN")
                      .requestMatchers("/api/**").authenticated()
                      .anyRequest().permitAll()
              ).addFilterBefore(new JwtTokenValidator(),BasicAuthenticationFilter.class)
              .csrf(AbstractHttpConfigurer::disable)
              .cors(cors->cors.configurationSource(CorsConfigurationSource())).build();
      
     
  }

    private CorsConfigurationSource CorsConfigurationSource() {
      return new CorsConfigurationSource() {
          @Override
          public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
              CorsConfiguration cfg = new CorsConfiguration();
              cfg.setAllowedOrigins(Arrays.asList(
                      "http://localhost:1111"
              ));
              cfg.setAllowedMethods(Collections.singletonList("*"));
              cfg.setAllowCredentials(true);
              cfg.setAllowedHeaders(Collections.singletonList("*"));
              cfg.setExposedHeaders(Arrays.asList("Authorization"));
              cfg.setMaxAge(3600L);
              return cfg;
          }
      };
    }

    @Bean
    PasswordEncoder passwordEncoder(){
      return  new BCryptPasswordEncoder();
    }
}
