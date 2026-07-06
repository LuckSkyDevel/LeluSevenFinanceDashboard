package com.finance.leluseven.conexaoplaid.infrastructure;

import com.finance.leluseven.conexaoplaid.domain.ConexaoPlaid;
import com.finance.leluseven.conexaoplaid.domain.IConexaoPlaidRepository;
import com.finance.leluseven.conexaoplaid.domain.vo.CodConexaoPlaid;
import com.finance.leluseven.conexaoplaid.domain.vo.CursorPlaid;
import com.finance.leluseven.plaid.domain.vo.PlaidToken;
import com.finance.leluseven.usuario.domain.vo.CodUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaConexaoPlaidRepositoryImpl implements IConexaoPlaidRepository {

    private final IJpaConexaoPlaidRepository jpa;
    private final ConexaoPlaidMapper mapper;

    public ConexaoPlaid salvaConexaoPlaid(ConexaoPlaid conexaoPlaid) {
        return mapper.toDomain(jpa.save(mapper.toEntity(conexaoPlaid)));
    }

    @Override
    public List<ConexaoPlaid> listaConexoesPlaidPorCodUsuario(CodUsuario codUsuario) {
        return jpa.findByCodUsuario(codUsuario.valor()).stream().map(mapper::toDomain).toList();
    }

    @Override
    public Optional<ConexaoPlaid> recuperaConexaoPlaidPorAccessToken(PlaidToken plaidToken) {
        return jpa.findByAccessToken(plaidToken.accessToken()).map(mapper::toDomain);
    }

    @Override
    public Optional<ConexaoPlaid> recuperaConexaoPlaidPorCodConexaoPlaid(CodConexaoPlaid codConexaoPlaid) {
        return jpa.findById(codConexaoPlaid.valor()).map(mapper::toDomain);
    }

    @Override
    public void inativaConexaoPlaid(CodConexaoPlaid codConexaoPlaid) {
        jpa.deleteConexaoPlaidByCodConexaoPlaid(codConexaoPlaid.valor(), false);
    }

    @Override
    public void atualizaPlaudCursor(CodConexaoPlaid codConexaoPlaid, CursorPlaid cursor) {
        jpa.updatePlaidCursor(codConexaoPlaid.valor(), cursor.valor());
    }
}
