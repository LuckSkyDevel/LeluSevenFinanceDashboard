package com.finance.lululeleseven.perfil.domain;

import java.util.List;
import java.util.Optional;

public interface IPerfilRepository {
    Optional<Perfil> recuperaPerfilPorCodigo(Long codigoPerfil);
    List<Perfil> recuperaPerfisPorCodigoUsuario(Long codigoUsuario);
    List<Perfil> listaTodosPerfis();
    Optional<Perfil> recuperaPerfilPorNome(String nomePerfil);
    Perfil salvar(Perfil perfil);
}
