package com.finance.leluseven.plaid.application;

import com.finance.leluseven.conexaoplaid.domain.ConexaoPlaid;
import com.finance.leluseven.conexaoplaid.domain.IConexaoPlaidRepository;
import com.finance.leluseven.plaid.domain.IPlaidRepository;
import com.finance.leluseven.plaid.domain.vo.PlaidToken;
import com.finance.leluseven.shared.exception.DataNotFoundException;
import com.finance.leluseven.transacao.domain.Transacao;
import com.finance.leluseven.usuario.domain.vo.CodUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarTransacoesPlaidUseCase {
    private final IPlaidRepository repo;
    private final IConexaoPlaidRepository repoConexao;

    public List<Transacao> execute(CodUsuario codUsuario, LocalDate inicio, LocalDate fim) {
        var conexaoPlaid = repoConexao.listaConexoesPlaidPorCodUsuario(codUsuario);

        if (conexaoPlaid.isEmpty()) {
            throw new DataNotFoundException("Transações não podem ser listadas para o usuario, pois não foi encontrado!");
        }

        return conexaoPlaid.stream().filter(ConexaoPlaid::isAtivo).flatMap(c -> {
            var plaidToken = PlaidToken.de(c.getAccessToken().valor(), c.getItemId().valor());
            return repo.listarTransacoes(plaidToken, inicio, fim).stream();
        }).toList();
    }
}
