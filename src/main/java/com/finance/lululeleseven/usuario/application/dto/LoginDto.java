package com.finance.lululeleseven.usuario.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    private String nomeUsuario;
    private String senha;
}
