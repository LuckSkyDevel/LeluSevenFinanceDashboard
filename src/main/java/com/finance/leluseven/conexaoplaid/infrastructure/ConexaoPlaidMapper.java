package com.finance.leluseven.conexaoplaid.infrastructure;

import com.finance.leluseven.conexaoplaid.domain.ConexaoPlaid;
import org.springframework.stereotype.Component;

@Component
public class ConexaoPlaidMapper {

    public ConexaoPlaid toDomain(ConexaoPlaidEntity entity) {
        return ConexaoPlaid.reconstituir(
                entity.getCodConexcaoPlaid(),
                entity.getCodUsuario(),
                entity.getAccessToken(),
                entity.getItemId(),
                entity.getInstituicao()
        );
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
