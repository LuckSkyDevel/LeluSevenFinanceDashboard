package com.finance.leluseven.perfil.infrastructure;

import com.finance.leluseven.usuario.infrastructure.UsuarioEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_perfil", schema = "seguranca")
public class PerfilEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -5113086501201308420L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_perfil")
    private Long codPerfil;

    @Column(name="nom_perfil", nullable = false)
    private String nomPerfil;

    @Column(name="des_perfil", nullable = false)
    private String desPerfil;

    @Column(name="dat_criacao", nullable = false)
    private LocalDate datCriacao = LocalDate.now();

    @Column(name="st_ativo", nullable = false)
    private Boolean stAtivo = true;

    @ManyToMany(mappedBy = "perfis", cascade=CascadeType.ALL)
    private Set<UsuarioEntity> usuarios;
}
