package com.kritagya.khandelwal.security.providers;

import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

public interface CustomAuthenticationProvider {

  /*
  1. Specific Provider will put Authentication login in this function
  2. Return the Authenticated Authentication object to Authentication Manager
   */
  Mono<Authentication> authenticate(Authentication authentication);

  /*
  Provides the logic to weather particular Authentication object is
  suppoerted for authentication by this provider or not.
   */
  Boolean supports(Authentication authentication);

}
