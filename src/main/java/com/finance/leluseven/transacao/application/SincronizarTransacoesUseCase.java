package com.finance.leluseven.transacao.application;

import com.finance.leluseven.conexaoplaid.domain.ConexaoPlaid;
import com.finance.leluseven.conexaoplaid.domain.IConexaoPlaidRepository;
import com.finance.leluseven.conexaoplaid.domain.vo.CursorPlaid;
import com.finance.leluseven.plaid.domain.IPlaidRepository;
import com.finance.leluseven.plaid.domain.vo.PlaidToken;
import com.finance.leluseven.shared.exception.DomainException;
import com.finance.leluseven.transacao.domain.ITransacaoRepository;
import com.finance.leluseven.transacao.domain.Transacao;
import com.finance.leluseven.usuario.domain.vo.CodUsuario;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SincronizarTransacoesUseCase {

    private final IPlaidRepository plaidRepository;
    private final ITransacaoRepository transacaoRepository;
    private final IConexaoPlaidRepository repoConexao;

    @Transactional
    public void execute(CodUsuario codUsuario) {
        var conexoes = repoConexao.listaConexoesPlaidPorCodUsuario(codUsuario);

        if (conexoes.isEmpty())
            throw new DomainException("Usuário não vinculado a nenhuma conta bancária");

        conexoes.stream().filter(ConexaoPlaid::isAtivo).forEach(conexo -> {
            var cursor = conexo.getPlaidCursor().valor();  // null na primeira vez
            boolean temMaisPaginas = true;

            while (temMaisPaginas) {
                var resultado = plaidRepository.sincronizarTransacoes(PlaidToken.de(conexo.getAccessToken().valor(), conexo.getItemId().valor()), cursor);

                // processa adicionadas e modificadas
                processarTransacoes(resultado.adicionadas(), codUsuario);
                processarTransacoes(resultado.modificadas(), codUsuario);

                // processa removidas
                resultado.removidasIds().forEach(transacaoRepository::deleteByPlaidId);

                cursor = resultado.novoCursor();
                temMaisPaginas = resultado.temMaisPaginas();
            }

            // salva o cursor atualizado para a próxima sincronização
            conexo.atualizarPlaidCursor(cursor);
            repoConexao.atualizaPlaudCursor(conexo.getCodConexaoPlaid(), CursorPlaid.de(cursor));
        });
    }

    private void processarTransacoes(List<Transacao> transacoes, CodUsuario codUsuario) {
        transacoes.forEach(t -> {
            transacaoRepository.findByPlaidTransacaoId(t.getPlaidTransacaoId()).ifPresentOrElse(existente -> {
                existente.atualizar(t.getDescricao(), t.getValor().valor(), t.getCategoria());
                transacaoRepository.save(existente);
            }, () -> {
                var nova = Transacao.de(t.getPlaidTransacaoId(), t.getDescricao(), t.getValor().valor(), t.getDataTransacao(), t.getCategoria(), codUsuario);
                transacaoRepository.save(nova);
            });
        });
    }
}
