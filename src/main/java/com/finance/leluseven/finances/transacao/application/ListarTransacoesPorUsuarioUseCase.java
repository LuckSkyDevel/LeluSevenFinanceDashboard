package com.finance.leluseven.finances.transacao.application;

import com.finance.leluseven.finances.transacao.domain.ITransacaoRepository;
import com.finance.leluseven.finances.transacao.domain.Transacao;
import com.finance.leluseven.shared.exception.DataNotFoundException;
import com.finance.leluseven.usuario.domain.IUsuarioRepository;
import com.finance.leluseven.usuario.domain.vo.NomeUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarTransacoesPorUsuarioUseCase {

    private final ITransacaoRepository repo;
    private final IUsuarioRepository repoUsuario;

    @Transactional
    public List<Transacao> execute(String username) {
        var user = repoUsuario.recuperarUsuarioPorNomeUsuario(NomeUsuario.de(username))
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        return repo.findByUsuarioId(user.getCodUsuario());
    }

    @Transactional
    public List<Transacao> executeForPeriod(String username, LocalDate inicio, LocalDate fim) {
        var user = repoUsuario.recuperarUsuarioPorNomeUsuario(NomeUsuario.de(username))
                .orElseThrow(() -> new DataNotFoundException("User not found"));
        return repo.findByUsuarioIdAndPeriodo(user.getCodUsuario(), inicio, fim);
    }
}
