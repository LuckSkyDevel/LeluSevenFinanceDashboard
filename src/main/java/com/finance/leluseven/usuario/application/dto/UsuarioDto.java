package com.finance.leluseven.usuario.application.dto;

import com.finance.leluseven.shared.exception.DomainException;
import com.finance.leluseven.usuario.domain.Usuario;
import com.finance.leluseven.usuario.domain.vo.CodUsuario;
import com.finance.leluseven.usuario.domain.vo.Email;
import com.finance.leluseven.usuario.domain.vo.NomeUsuario;

import java.util.List;

public record UsuarioDto(CodUsuario codUsuario, NomeUsuario nome, Email email, List<String> perfis, String accessToken,
                         String refreshToken) {
    public UsuarioDto {
        if (refreshToken == null || refreshToken.isEmpty()) {
            throw new DomainException("Usuário não foi autenticado!");
        }
    }

    public static UsuarioDto de(Usuario usuario, String accessToken, String refreshToken) {
        return new UsuarioDto(
                usuario.getCodUsuario(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getPerfis().stream().map(p -> p.getNomePerfil().nome()).toList(),
                accessToken,
                refreshToken
        );
    }
}
