package com.finance.lululeleseven.refreshtoken.domain;

import com.finance.lululeleseven.refreshtoken.domain.vo.CodRefreshToken;
import com.finance.lululeleseven.refreshtoken.domain.vo.Dispositivo;
import com.finance.lululeleseven.usuario.domain.Usuario;

import java.time.LocalDateTime;

public class RefreshToken {
    private CodRefreshToken codRefreshToken;
    private String token;
    private LocalDateTime datExpiracao;
    private Dispositivo dispositivo;
    private Boolean isRevogado;
    private Usuario usuario;
    private LocalDateTime datCriacao;

    public static RefreshToken criar(String token, LocalDateTime datExpiracao,
                                     String dispositivo, Boolean isRevogado, Usuario usuario, LocalDateTime datCriacao) {
        var refreshToken = new RefreshToken();
        refreshToken.token = token;
        refreshToken.datExpiracao = datExpiracao;
        refreshToken.dispositivo = Dispositivo.de(dispositivo);
        refreshToken.isRevogado = isRevogado;
        refreshToken.usuario = usuario;
        refreshToken.datCriacao = datCriacao;
        return refreshToken;
    }

    public static RefreshToken recriar(Long codRefreshToken, String token, LocalDateTime datExpiracao,
                                       String dispositivo, Boolean isRevogado, Usuario usuario) {
        var refreshToken = new RefreshToken();
        refreshToken.codRefreshToken = CodRefreshToken.de(codRefreshToken);
        refreshToken.token = token;
        refreshToken.datExpiracao = datExpiracao;
        refreshToken.dispositivo = Dispositivo.de(dispositivo);
        refreshToken.isRevogado = isRevogado;
        refreshToken.usuario = usuario;
        return refreshToken;
    }

    public void atualizarRefreshToken(String token) {
        this.token = token;
    }
}
