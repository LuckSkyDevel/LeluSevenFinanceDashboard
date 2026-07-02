package com.finance.leluseven.usuario.infrastructure;

import com.finance.leluseven.perfil.infrastructure.PerfilEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_usuario")
public class UsuarioEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_usuario")
    private Long codUsuario;

    @Column(name = "nom_usuario", nullable = false, unique = true)
    private String nomUsuario;

    @Column(name = "des_email")
    private String desEmail;

    @Column(name = "str_senha_hash", nullable = false)
    private String senhaHash;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tb_usuario_perfil",
            joinColumns = @JoinColumn(name = "cod_usuario"),
            inverseJoinColumns = @JoinColumn(name = "cod_perfil")
    )
    private Set<PerfilEntity> perfis;

    @Column(name = "dat_criacao")
    private LocalDate datCriacao = LocalDate.now();

    @Serial
    private static final long serialVersionUID = 1L;

    public UsuarioEntity(String nomUsuario, String senhaHash, Set<PerfilEntity> perfis) {
        this.nomUsuario = nomUsuario;
        this.senhaHash = senhaHash;
        this.perfis = perfis;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return perfis.stream()
                .map(p -> new SimpleGrantedAuthority("ROLE_" + p.getNomPerfil().toUpperCase()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return senhaHash;
    }

    @Override
    public String getUsername() {
        return nomUsuario;
    }
}
