package com.erich.dev.springbootapirestwebflux.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenDto {

    private String token;

    private String bearer;
}
