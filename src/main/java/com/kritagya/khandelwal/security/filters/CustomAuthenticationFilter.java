package com.kritagya.khandelwal.security.filters;

import com.kritagya.khandelwal.security.converters.CustomAuthenticationConverter;
import com.kritagya.khandelwal.security.managers.CustomAuthenticationManager;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class CustomAuthenticationFilter implements WebFilter {

  private final CustomAuthenticationConverter customAuthenticationConverter;
  private final CustomAuthenticationManager customAuthenticationManager;
  private final ServerWebExchangeMatcher requestMatcher;

  /*
  1. Convert the Exchange to unauthenticated Authentication object
  2. pass that Authentication object to Authentication Manager
  3. Save returned Authentication object into Security Context Holder
   */

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
    return requestMatcher.matches(exchange)
      .filter(ServerWebExchangeMatcher.MatchResult::isMatch)
      .switchIfEmpty(chain.filter(exchange).then(Mono.empty()))
      .flatMap(matchResult -> customAuthenticationConverter.convert(exchange))
      .flatMap(customAuthenticationManager::authenticate)
      .flatMap(authentication -> onAuthenticationSuccess(authentication, new WebFilterExchange(exchange, chain)))
      .then();
  }

  private Mono<Void> onAuthenticationSuccess(Authentication authentication, WebFilterExchange exchange) {
    ServerWebExchange serverWebExchange = exchange.getExchange();
    SecurityContextImpl securityContext = new SecurityContextImpl();
    securityContext.setAuthentication(authentication);
    return exchange.getChain().filter(serverWebExchange)
      .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(securityContext)));
  }

}
