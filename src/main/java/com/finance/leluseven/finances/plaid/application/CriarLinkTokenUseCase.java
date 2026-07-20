package com.finance.leluseven.finances.plaid.application;

import com.finance.leluseven.finances.plaid.domain.ProvedorOpenBankingPort;
import com.finance.leluseven.shared.exception.DataNotFoundException;
import com.finance.leluseven.usuario.domain.IUsuarioRepository;
import com.finance.leluseven.usuario.domain.vo.NomeUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CriarLinkTokenUseCase {
    private final ProvedorOpenBankingPort provedorOpenBanking;
    private final IUsuarioRepository repoUsuario;

    public String execute(String username) {
        var user = repoUsuario.recuperarUsuarioPorNomeUsuario(NomeUsuario.de(username))
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        return provedorOpenBanking.criarLinkToken(user.getCodUsuario().valor().toString());
    }
}
