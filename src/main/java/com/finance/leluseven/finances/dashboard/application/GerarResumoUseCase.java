package com.finance.leluseven.finances.dashboard.application;

import com.finance.leluseven.finances.dashboard.domain.RelatorioFinanceiro;
import com.finance.leluseven.finances.transacao.domain.ITransacaoRepository;
import com.finance.leluseven.shared.exception.DataNotFoundException;
import com.finance.leluseven.shared.exception.DomainException;
import com.finance.leluseven.usuario.domain.IUsuarioRepository;
import com.finance.leluseven.usuario.domain.vo.NomeUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class GerarResumoUseCase {

    private final ITransacaoRepository transacaoRepository;
    private final IUsuarioRepository usuarioRepository;

    @Transactional
    public RelatorioFinanceiro execute(String nomeUsuario, LocalDate inicio, LocalDate fim) {
        var usuario = usuarioRepository.recuperarUsuarioPorNomeUsuario(NomeUsuario.de(nomeUsuario))
                .orElseThrow(() -> new DataNotFoundException("Usuário não encontrado!"));

        validarPeriodo(inicio, fim);

        var transacoes = transacaoRepository.findByUsuarioIdAndPeriodo(usuario.getCodUsuario(), inicio, fim);

        return RelatorioFinanceiro.gerar(usuario.getCodUsuario(), inicio, fim, transacoes);
    }

    private void validarPeriodo(LocalDate inicio, LocalDate fim) {
        if (inicio == null || fim == null)
            throw new DomainException("Informe o período (data de início e fim) para gerar o relatório!");

        if (inicio.isAfter(fim))
            throw new DomainException("A data de início não pode ser posterior à data de fim!");
    }
}
