package com.kritagya.khandelwal.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class HelloController {

  @GetMapping("/hello")
  @PreAuthorize("hasRole('ROLE_CustomRole')")
  public Mono<String> sayHello() {
    return Mono.just("Hello");
  }

}
