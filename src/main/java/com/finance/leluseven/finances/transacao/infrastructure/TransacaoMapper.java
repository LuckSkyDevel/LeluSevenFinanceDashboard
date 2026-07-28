package com.finance.leluseven.finances.transacao.infrastructure;

import com.finance.leluseven.finances.transacao.domain.Transacao;
import com.finance.leluseven.finances.transacao.domain.vo.CodTransacao;
import com.finance.leluseven.finances.transacao.domain.vo.Valor;
import com.finance.leluseven.usuario.domain.vo.CodUsuario;
import com.finance.leluseven.usuario.infrastructure.UsuarioEntity;
import org.springframework.stereotype.Component;

@Component
public class TransacaoMapper {

    public Transacao toDomain(TransacaoEntity entity) {
        return new Transacao.Builder()
                .codigoTransacao(CodTransacao.de(entity.getCodtransacao()))
                .plaidTransacaoId(entity.getPlaidTransacaoId())
                .descricao(entity.getDescricao())
                .valor(Valor.de(entity.getValor()))
                .dataTransacao(entity.getDatTransacao())
                .categoria(entity.getCategoria())
                .codUsuario(CodUsuario.de(entity.getUsuario().getCodUsuario()))
                .build();
    }

    public TransacaoEntity toEntity(Transacao domain, UsuarioEntity usuario) {
        var entity = new TransacaoEntity();
        entity.setPlaidTransacaoId(domain.getPlaidTransacaoId());
        entity.setDescricao(domain.getDescricao());
        entity.setCategoria(domain.getCategoria());
        entity.setContaId(domain.getContaId());
        entity.setValor(domain.getValor().valor());
        entity.setDatTransacao(domain.getDataTransacao());
        entity.setUsuario(usuario);

        return entity;
    }
}
