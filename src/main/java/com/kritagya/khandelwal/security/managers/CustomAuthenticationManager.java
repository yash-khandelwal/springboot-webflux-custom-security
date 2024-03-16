package com.kritagya.khandelwal.security.managers;

import com.kritagya.khandelwal.security.providers.ReverseAuthenticationProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class CustomAuthenticationManager implements ReactiveAuthenticationManager {

  private final ReverseAuthenticationProvider reverseAuthenticationProvider;

  /*
  1. Check the suitable Authentication Provider for Authentication object
  2. pass the Authentication object to that Provider
  3. return the output Authentication back to Authentication Manager
   */

  @Override
  public Mono<Authentication> authenticate(Authentication authentication) {
    return Flux.just(reverseAuthenticationProvider)
      .filter(provider -> provider.supports(authentication))
      .next()
      .flatMap(provider -> provider.authenticate(authentication));
  }

}
