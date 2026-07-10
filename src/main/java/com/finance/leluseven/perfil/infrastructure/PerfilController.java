package com.finance.leluseven.perfil.infrastructure;

import com.finance.leluseven.perfil.application.CriaPerfilUseCase;
import com.finance.leluseven.perfil.application.ListaPerfisUseCase;
import com.finance.leluseven.perfil.application.RecuperaPerfilUseCase;
import com.finance.leluseven.perfil.application.dto.PerfilDto;
import com.finance.leluseven.perfil.domain.Perfil;
import com.finance.leluseven.shared.infrastructure.payload.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/perfil")
@RequiredArgsConstructor
public class PerfilController {
    private final CriaPerfilUseCase criaPerfilUseCase;
    private final RecuperaPerfilUseCase recuperaPerfilUseCase;
    private final ListaPerfisUseCase listaPerfisUseCase;

    @GetMapping("/{codPerfil}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAG')")
    public ResponseEntity<ApiResponse<Perfil>> recuperaPerfilPorCodigoPerfil(@PathVariable Long codPerfil) {
        var response = ApiResponse.success(recuperaPerfilUseCase.execute(codPerfil));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{nomPerfil}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAG')")
    public ResponseEntity<ApiResponse<Perfil>> recuperarPerfilPorNomePerfil(@PathVariable String nomPerfil) {
        var response = ApiResponse.success(recuperaPerfilUseCase.execute(nomPerfil));

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Perfil>>> listaTodosPerfis() {
        var response = ApiResponse.success(listaPerfisUseCase.execute());

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAG')")
    public ResponseEntity<ApiResponse<Perfil>> criaPerfil(@Valid @RequestBody PerfilDto perfil, UriComponentsBuilder uriBuilder) {
        var perfilBanco = criaPerfilUseCase.executa(perfil);

        var location = uriBuilder.path("/perfil/{codPerfil}").buildAndExpand(perfilBanco.getCodigoPerfil().valor()).toUri();

        var response = ApiResponse.created(perfilBanco, "Perfil foi criado com sucesso!");

        return ResponseEntity.created(location).body(response);
    }
}
