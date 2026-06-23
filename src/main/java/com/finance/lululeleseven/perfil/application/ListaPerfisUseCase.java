package com.finance.lululeleseven.perfil.application;

import com.finance.lululeleseven.perfil.domain.IPerfilRepository;
import com.finance.lululeleseven.perfil.domain.Perfil;
import com.finance.lululeleseven.shared.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListaPerfisUseCase {
    private final IPerfilRepository repo;

    public List<Perfil> execute() {
        var perfis = repo.listaTodosPerfis();

        if(perfis.isEmpty()) {
            throw new DataNotFoundException("Nenhum perfil encontrado");
        }

        return perfis;
    }
}
