package com.finance.lululeleseven.usuario.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistroDto {
    private String nome;
    private String email;
    private String senha;
}
