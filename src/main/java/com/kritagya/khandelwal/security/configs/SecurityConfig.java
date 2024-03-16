package com.kritagya.khandelwal.security.configs;

import com.kritagya.khandelwal.security.converters.CustomAuthenticationConverter;
import com.kritagya.khandelwal.security.filters.CustomAuthenticationFilter;
import com.kritagya.khandelwal.security.managers.CustomAuthenticationManager;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@Configuration
@AllArgsConstructor
public class SecurityConfig {

  private final CustomAuthenticationConverter authenticationConverter;
  private final CustomAuthenticationManager authenticationManager;

  @Bean
  public SecurityWebFilterChain chain(ServerHttpSecurity http) {
    return http
      .addFilterAt(
        new CustomAuthenticationFilter(
          authenticationConverter,
          authenticationManager,
          new PathPatternParserServerWebExchangeMatcher("/**")
        ),
        SecurityWebFiltersOrder.AUTHENTICATION
      )
      .authorizeExchange(authorizeExchangeSpec -> authorizeExchangeSpec.anyExchange().permitAll())
      .build();
  }

}
