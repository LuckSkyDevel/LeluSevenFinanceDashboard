package com.finance.leluseven.finances.dashboard.application;

import com.finance.leluseven.finances.dashboard.domain.vo.GastoCategoria;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GerarGraficoGastosUseCase {

    private final GerarResumoUseCase gerarResumoUseCase;

    public List<GastoCategoria> execute(String nomeUsuario, LocalDate inicio, LocalDate fim) {
        return gerarResumoUseCase.execute(nomeUsuario, inicio, fim).getGastosPorCategoria();
    }
}
