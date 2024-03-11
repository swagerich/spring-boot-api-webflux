package com.erich.dev.springbootapirestwebflux.entity;


import com.erich.dev.springbootapirestwebflux.entity.auth.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "users")
public class User implements UserDetails {

    @Id
    private String id;

    private String userName;

    private String mail;

    @JsonIgnore
    private String password;

    private String roles;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      return  Stream.of(roles.split(", ")).map(SimpleGrantedAuthority::new).toList();
    }

    @Override
    public String getUsername() {
        return userName;
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
