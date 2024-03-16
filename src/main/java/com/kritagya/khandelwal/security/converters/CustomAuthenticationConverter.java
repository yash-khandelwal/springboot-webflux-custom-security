package com.kritagya.khandelwal.security.converters;

import com.kritagya.khandelwal.security.authentications.CustomAuthentication;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
@AllArgsConstructor
public class CustomAuthenticationConverter {

  public Mono<Authentication> convert(ServerWebExchange exchange) {
    return Mono.just(exchange.getRequest().getHeaders())
      .map(headers -> CustomAuthentication.builder()
        .authenticated(false)
        .credentials(getHeaderSingleValue(headers, "password"))
        .name(getHeaderSingleValue(headers, "username"))
        .build());
  }

  private String getHeaderSingleValue(HttpHeaders headers, String key) {
    return Optional.ofNullable(headers.get(key))
      .map(l -> l.size() == 1 ? l.get(0) : null)
      .orElse(null);
  }

}
