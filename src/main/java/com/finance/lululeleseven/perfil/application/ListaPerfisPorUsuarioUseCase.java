package com.finance.lululeleseven.perfil.application;

import com.finance.lululeleseven.perfil.domain.IPerfilRepository;
import com.finance.lululeleseven.perfil.domain.Perfil;
import com.finance.lululeleseven.shared.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListaPerfisPorUsuarioUseCase {
    private final IPerfilRepository repo;

    public List<Perfil> execute(Long codUsuario) {
        if (codUsuario == null ||  codUsuario <= 0) {
            throw new DataNotFoundException("Usuário deve ser informado!");
        }

        return repo.recuperaPerfisPorCodigoUsuario(codUsuario);
    }
}
