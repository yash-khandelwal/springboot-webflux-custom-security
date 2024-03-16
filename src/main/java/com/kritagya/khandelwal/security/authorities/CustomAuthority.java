package com.kritagya.khandelwal.security.authorities;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
public class CustomAuthority implements GrantedAuthority {

  private String authority;

  @Override
  public String getAuthority() {
    return this.authority;
  }

}
