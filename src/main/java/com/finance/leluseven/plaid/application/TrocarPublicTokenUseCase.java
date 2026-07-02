package com.finance.leluseven.plaid.application;

import com.finance.leluseven.conexaoplaid.domain.ConexaoPlaid;
import com.finance.leluseven.conexaoplaid.domain.IConexaoPlaidRepository;
import com.finance.leluseven.plaid.domain.IPlaidRepository;
import com.finance.leluseven.usuario.domain.vo.CodUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrocarPublicTokenUseCase {
    private final IPlaidRepository plaidRepository;
    private final IConexaoPlaidRepository repoConexao;

    public void execute(String publicToken, CodUsuario codUsuario) {
        // troca o public token pelo access token
        var resposta = plaidRepository.trocarPublicToken(publicToken);

        // Cria o vinculo entre usuario e o plaid
        var conexao = ConexaoPlaid.vincularUsuario(codUsuario.valor(), resposta.getAccessToken().valor(), resposta.getItemId().valor(), resposta.getInstituicao());

        repoConexao.salvaConexaoPlaid(conexao);
    }
}
