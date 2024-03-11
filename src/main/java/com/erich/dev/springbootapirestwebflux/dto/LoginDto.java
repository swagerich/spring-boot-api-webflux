package com.erich.dev.springbootapirestwebflux.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDto {

    private String userName;

    private String password;
}
