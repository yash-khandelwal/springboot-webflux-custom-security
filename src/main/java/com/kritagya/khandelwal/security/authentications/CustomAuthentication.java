package com.kritagya.khandelwal.security.authentications;

import com.kritagya.khandelwal.security.authorities.CustomAuthority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@Data
@Builder
public class CustomAuthentication implements Authentication {

  private final String credentials;
  private final String principal; // user identifier, username in our case
  private final Boolean authenticated;
  private final String name;
  private final List<CustomAuthority> authorities;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

  @Override
  public String getCredentials() {
    return this.credentials;
  }

  @Override
  public Object getDetails() {
    return null;
  }

  @Override
  public String getPrincipal() {
    return this.principal;
  }

  @Override
  public boolean isAuthenticated() {
    return this.authenticated;
  }

  @Override
  public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

  }

  @Override
  public String getName() {
    return this.name;
  }
}
