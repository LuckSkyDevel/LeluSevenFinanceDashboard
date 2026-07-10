package com.finance.leluseven.finances.plaid.application;

import com.finance.leluseven.finances.conexaoplaid.domain.ConexaoPlaid;
import com.finance.leluseven.finances.conexaoplaid.domain.IConexaoPlaidRepository;
import com.finance.leluseven.finances.plaid.domain.ProvedorOpenBankingPort;
import com.finance.leluseven.usuario.domain.vo.CodUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrocarPublicTokenUseCase {
    private final ProvedorOpenBankingPort provedorOpenBanking;
    private final IConexaoPlaidRepository repoConexao;

    public void execute(String publicToken, CodUsuario codUsuario) {
        // troca o public token pelo access token
        var resposta = provedorOpenBanking.trocarPublicToken(publicToken);

        // Cria o vinculo entre usuario e o plaid
        var conexao = ConexaoPlaid.vincularUsuario(codUsuario.valor(), resposta.getAccessToken().valor(),
                resposta.getItemId().valor(), resposta.getInstituicao().valor());

        repoConexao.salvaConexaoPlaid(conexao);
    }
}
