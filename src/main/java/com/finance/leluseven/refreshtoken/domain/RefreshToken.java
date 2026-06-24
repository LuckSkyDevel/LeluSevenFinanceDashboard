package com.finance.leluseven.refreshtoken.domain;

import com.finance.leluseven.refreshtoken.domain.vo.CodRefreshToken;
import com.finance.leluseven.refreshtoken.domain.vo.Dispositivo;
import com.finance.leluseven.usuario.domain.Usuario;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class RefreshToken {
    private CodRefreshToken codRefreshToken;
    private String rToken;
    private LocalDateTime datExpiracao;
    private Dispositivo dispositivo;
    private Boolean isRevogado;
    private Usuario usuario;
    private LocalDateTime datCriacao;

    public static RefreshToken criar(String rToken, Long expiracao, String dispositivo, Usuario usuario) {
        var refreshToken = new RefreshToken();
        refreshToken.rToken = rToken;
        refreshToken.datExpiracao = LocalDateTime.now().plusSeconds(expiracao / 1000);;
        refreshToken.dispositivo = Dispositivo.de(dispositivo);
        refreshToken.isRevogado = false;
        refreshToken.usuario = usuario;
        refreshToken.datCriacao = LocalDateTime.now();
        return refreshToken;
    }

    public static RefreshToken recriar(Long codRefreshToken, String rToken, LocalDateTime datExpiracao,
                                       String dispositivo, Boolean isRevogado, Usuario usuario) {
        var refreshToken = new RefreshToken();
        refreshToken.codRefreshToken = CodRefreshToken.de(codRefreshToken);
        refreshToken.rToken = rToken;
        refreshToken.datExpiracao = datExpiracao;
        refreshToken.dispositivo = Dispositivo.de(dispositivo);
        refreshToken.isRevogado = isRevogado;
        refreshToken.usuario = usuario;
        return refreshToken;
    }

    public boolean isValido() {
        return !isRevogado && LocalDateTime.now().isBefore(datExpiracao);
    }

    public void revogar() {
        this.datExpiracao = LocalDateTime.now();
        this.isRevogado = true;
    }
}
