package com.erich.dev.springbootapirestwebflux.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistryUserDto {

    private String userName;

    private String email;

    private String password;
}
