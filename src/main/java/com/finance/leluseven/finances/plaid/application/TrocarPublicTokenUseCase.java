package com.finance.leluseven.finances.plaid.application;

import com.finance.leluseven.finances.conexaoplaid.domain.ConexaoPlaid;
import com.finance.leluseven.finances.conexaoplaid.domain.IConexaoPlaidRepository;
import com.finance.leluseven.finances.plaid.domain.ProvedorOpenBankingPort;
import com.finance.leluseven.shared.exception.DataNotFoundException;
import com.finance.leluseven.usuario.domain.IUsuarioRepository;
import com.finance.leluseven.usuario.domain.vo.CodUsuario;
import com.finance.leluseven.usuario.domain.vo.NomeUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrocarPublicTokenUseCase {
    private final ProvedorOpenBankingPort provedorOpenBanking;
    private final IConexaoPlaidRepository repoConexao;
    private final IUsuarioRepository repoUsuario;

    public void execute(String publicToken, String username) {
        var user = repoUsuario.findByNomUsuario(NomeUsuario.de(username))
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        // troca o public token pelo access token
        var resposta = provedorOpenBanking.trocarPublicToken(publicToken);

        // Cria o vinculo entre usuario e o plaid
        var conexao = ConexaoPlaid.vincularUsuario(user.getCodUsuario().valor(), resposta.getAccessToken().valor(),
                resposta.getItemId().valor(), resposta.getInstituicao().valor());

        repoConexao.salvaConexaoPlaid(conexao);
    }
}
