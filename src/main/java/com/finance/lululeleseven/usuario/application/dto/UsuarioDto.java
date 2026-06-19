package com.finance.lululeleseven.usuario.application.dto;

import com.finance.lululeleseven.perfil.application.dto.PerfilDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto {
    private Long codUsuario;
    private String nome;
    private String email;
    private List<PerfilDto> perfiis;
    private String accessToken;
    private String refreshToken;
}
