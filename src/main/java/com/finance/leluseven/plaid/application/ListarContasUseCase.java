package com.finance.leluseven.plaid.application;

import com.finance.leluseven.conexaoplaid.domain.ConexaoPlaid;
import com.finance.leluseven.conexaoplaid.domain.IConexaoPlaidRepository;
import com.finance.leluseven.plaid.domain.ContaBancaria;
import com.finance.leluseven.plaid.domain.IPlaidRepository;
import com.finance.leluseven.plaid.domain.vo.PlaidToken;
import com.finance.leluseven.shared.exception.DataNotFoundException;
import com.finance.leluseven.usuario.domain.IUsuarioRepository;
import com.finance.leluseven.usuario.domain.vo.CodUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarContasUseCase {
    private final IPlaidRepository repo;
    private final IUsuarioRepository repoUsuario;
    private final IConexaoPlaidRepository repoConexao;

    public List<ContaBancaria> execute(CodUsuario codUsuario) {
        var conexoes = repoConexao.listaConexoesPlaidPorCodUsuario(codUsuario);

        if (conexoes == null || conexoes.isEmpty()) {
            throw new DataNotFoundException("Não foi possÍvel listar as contas pelo codigo de usuario informado!");
        }

        return conexoes.stream().filter(ConexaoPlaid::isAtivo).flatMap(c -> {
            var plaidToken = PlaidToken.de(c.getAccessToken().valor(), c.getItemId().valor());
            return repo.listarContas(plaidToken).stream();
        }).toList();
    }
}
