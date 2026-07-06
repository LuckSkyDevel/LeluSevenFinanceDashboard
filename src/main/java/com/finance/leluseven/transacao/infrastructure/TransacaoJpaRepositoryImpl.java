package com.finance.leluseven.transacao.infrastructure;

import com.finance.leluseven.shared.exception.DomainException;
import com.finance.leluseven.transacao.domain.ITransacaoRepository;
import com.finance.leluseven.transacao.domain.Transacao;
import com.finance.leluseven.transacao.domain.vo.CodTransacao;
import com.finance.leluseven.usuario.domain.vo.CodUsuario;
import com.finance.leluseven.usuario.infrastructure.IUsuarioJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TransacaoJpaRepositoryImpl implements ITransacaoRepository {

    private final ITransacaoJpaRepository jpa;
    private final IUsuarioJpaRepository jpaUsuario;
    private final TransacaoMapper mapper;

    @Override
    public Transacao save(Transacao transacao) {
        var usuario = jpaUsuario.findById(transacao.getCodUsuario().valor()).orElseThrow(
                () -> new DomainException("NÃo É possÍvel salvar a transação, pois o usuário não existe"));

        var transacaoEntity = mapper.toEntity(transacao, usuario);
        return mapper.toDomain(jpa.save(transacaoEntity));
    }

    @Override
    public List<Transacao> findByUsuarioId(CodUsuario codUsuario) {
        return jpa.findByUsuarioCodUsuario(codUsuario.valor()).stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Transacao> findByUsuarioIdAndPeriodo(CodUsuario codUsuario, LocalDate inicio, LocalDate fim) {
        return jpa.findByUsuarioIdAndPeriodo(codUsuario.valor(), inicio, fim).stream().map(mapper::toDomain).toList();
    }

    @Override
    public boolean existsByPlaidId(String plaidTransacaoId) {
        return jpa.existsByPlaidTransacaoId(plaidTransacaoId);
    }

    @Override
    public Optional<Transacao> findByPlaidTransacaoId(String plaidTransacaoId) {
        return jpa.findByPlaidTransacaoId(plaidTransacaoId).map(mapper::toDomain);
    }

    @Override
    public Optional<Transacao> findById(CodTransacao codTransacao) {
        return jpa.findById(codTransacao.valor()).map(mapper::toDomain);
    }

    @Override
    public void deleteByPlaidId(String plaidTransacaoId) {
        jpa.inativaTransacaoByPlaidTrasacaoId(plaidTransacaoId);
    }
}
