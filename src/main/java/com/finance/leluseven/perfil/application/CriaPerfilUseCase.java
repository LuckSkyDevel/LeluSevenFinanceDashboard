package com.finance.leluseven.perfil.application;

import com.finance.leluseven.perfil.application.dto.PerfilDto;
import com.finance.leluseven.perfil.domain.IPerfilRepository;
import com.finance.leluseven.perfil.domain.Perfil;
import com.finance.leluseven.perfil.domain.vo.NomePerfil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CriaPerfilUseCase {
    private final IPerfilRepository repo;

    public Perfil executa(PerfilDto perfilDto) {
        var perfil = new Perfil.Builder()
                .nomePerfil(NomePerfil.de(perfilDto.nome()))
                .descricao(perfilDto.descricao())
                .build();

        return repo.salvar(perfil);
    }
}
