package com.finance.leluseven.finances.conexaoplaid.infrastructure;

import com.finance.leluseven.finances.conexaoplaid.domain.ConexaoPlaid;
import com.finance.leluseven.finances.conexaoplaid.domain.vo.AccessTokenPlaid;
import com.finance.leluseven.finances.conexaoplaid.domain.vo.CodConexaoPlaid;
import com.finance.leluseven.finances.conexaoplaid.domain.vo.Instituicao;
import com.finance.leluseven.finances.conexaoplaid.domain.vo.ItemIdPlaid;
import com.finance.leluseven.usuario.domain.vo.CodUsuario;
import org.springframework.stereotype.Component;

@Component
public class ConexaoPlaidMapper {

    public ConexaoPlaid toDomain(ConexaoPlaidEntity entity) {
        return new ConexaoPlaid.Builder()
                .codConexaoPlaid(CodConexaoPlaid.de(entity.getCodConexcaoPlaid()))
                .codUsuario(CodUsuario.de(entity.getCodUsuario()))
                .accessTokenPlaid(AccessTokenPlaid.de(entity.getAccessToken()))
                .itemIdPlaid(ItemIdPlaid.de(entity.getItemId()))
                .instituicao(Instituicao.de(entity.getInstituicao()))
                .build();
    }

    public ConexaoPlaidEntity toEntity(ConexaoPlaid domain) {
        var entity = new ConexaoPlaidEntity();
        entity.setCodUsuario(domain.getCodUsuario().valor());
        entity.setAccessToken(domain.getAccessToken().valor());
        entity.setItemId(domain.getItemId().valor());
        entity.setInstituicao(domain.getInstituicao().valor());
        return entity;
    }
}
