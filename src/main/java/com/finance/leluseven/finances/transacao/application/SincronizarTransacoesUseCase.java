package com.finance.leluseven.finances.transacao.application;

import com.finance.leluseven.finances.conexaoplaid.domain.ConexaoPlaid;
import com.finance.leluseven.finances.conexaoplaid.domain.IConexaoPlaidRepository;
import com.finance.leluseven.finances.conexaoplaid.domain.vo.CursorPlaid;
import com.finance.leluseven.finances.plaid.domain.ProvedorOpenBankingPort;
import com.finance.leluseven.finances.plaid.domain.vo.PlaidToken;
import com.finance.leluseven.shared.exception.DataNotFoundException;
import com.finance.leluseven.shared.exception.DomainException;
import com.finance.leluseven.finances.transacao.domain.ITransacaoRepository;
import com.finance.leluseven.finances.transacao.domain.Transacao;
import com.finance.leluseven.usuario.domain.IUsuarioRepository;
import com.finance.leluseven.usuario.domain.vo.CodUsuario;
import com.finance.leluseven.usuario.domain.vo.NomeUsuario;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SincronizarTransacoesUseCase {

    private final ProvedorOpenBankingPort plaidRepository;
    private final ITransacaoRepository transacaoRepository;
    private final IConexaoPlaidRepository repoConexao;
    private final IUsuarioRepository repoUsuario;

    @Transactional
    public void execute(String username) {
        var user = repoUsuario.recuperarUsuarioPorNomeUsuario(NomeUsuario.de(username)).orElseThrow(() -> new DataNotFoundException("Usuário não encontrado!"));
        var codUsuario = user.getCodUsuario();

        var conexoes = repoConexao.listaConexoesPlaidPorCodUsuario(codUsuario);

        if (conexoes.isEmpty())
            throw new DomainException("Usuário não vinculado a nenhuma conta bancária");

        conexoes.stream().filter(ConexaoPlaid::isAtivo).forEach(conexao -> {
            var cursor = conexao.getPlaidCursor().valor();  // null na primeira vez
            boolean temMaisPaginas = true;

            while (temMaisPaginas) {
                var resultado = plaidRepository.sincronizarTransacoes(PlaidToken.de(conexao.getAccessToken().valor(), conexao.getItemId().valor()), cursor);

                // processa adicionadas e modificadas
                processarTransacoes(resultado.adicionadas(), codUsuario);
                processarTransacoes(resultado.modificadas(), codUsuario);

                // processa removidas
                resultado.removidasIds().forEach(transacaoRepository::deleteByPlaidId);

                cursor = resultado.novoCursor();
                temMaisPaginas = resultado.temMaisPaginas();
            }

            // salva o cursor atualizado para a próxima sincronização
            conexao.atualizarPlaidCursor(cursor);
            repoConexao.atualizaPlaidCursor(conexao.getCodConexaoPlaid(), CursorPlaid.de(cursor));
        });
    }

    private void processarTransacoes(List<Transacao> transacoes, CodUsuario codUsuario) {
        transacoes.forEach(t -> transacaoRepository.findByPlaidTransacaoId(t.getPlaidTransacaoId()).ifPresentOrElse(existente -> {
            existente.atualizar(t.getDescricao(), t.getValor().valor(), t.getCategoria());
            transacaoRepository.save(existente);
        }, () -> {
            var nova = Transacao.de(t.getPlaidTransacaoId(), t.getDescricao(), t.getValor().valor(), t.getDataTransacao(), t.getCategoria(), codUsuario);
            transacaoRepository.save(nova);
        }));
    }
}
