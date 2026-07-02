package com.finance.leluseven.refreshtoken.infrastructure;

import com.finance.leluseven.usuario.infrastructure.UsuarioEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_refresh_token", schema = "seguranca")
public class RefreshTokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "refresh_token_seq")
    @SequenceGenerator(name = "refresh_token_seq", sequenceName = "tb_refresh_token_cod_token_seq", allocationSize = 1)
    @Column(name = "cod_refresh_token")
    private Long codRefreshToken;

    @Column(name = "str_refresh_token", unique = true)
    private String refreshToken;

    @Column(name = "dat_expiracao")
    private LocalDateTime datExpiracao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cod_usuairio")
    private UsuarioEntity usuario;

    @Column(name = "sta_revogado")
    private Boolean revogado = false;

    @Column(name = "des_dispositivo")
    private String dispositivo;

    @Column(name = "dat_criacao")
    private LocalDateTime datCriacao = LocalDateTime.now();
}
