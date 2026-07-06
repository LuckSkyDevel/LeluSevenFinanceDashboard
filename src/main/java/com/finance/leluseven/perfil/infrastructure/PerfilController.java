package com.finance.leluseven.perfil.infrastructure;

import com.finance.leluseven.perfil.application.CriaPerfilUseCase;
import com.finance.leluseven.perfil.application.ListaPerfisUseCase;
import com.finance.leluseven.perfil.application.RecuperaPerfilUseCase;
import com.finance.leluseven.perfil.application.dto.PerfilDto;
import com.finance.leluseven.perfil.domain.Perfil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Perfil> recuperaPerfilPorCodigoPerfil(@PathVariable Long codPerfil) {
        return ResponseEntity.ok(recuperaPerfilUseCase.execute(codPerfil));
    }

    @GetMapping("/{nomPerfil}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAG')")
    public ResponseEntity<Perfil> recuperarPerfilPorNomePerfil(@PathVariable String nomPerfil) {
        return ResponseEntity.ok(recuperaPerfilUseCase.execute(nomPerfil));
    }

    @GetMapping
    public ResponseEntity<List<Perfil>> listaTodosPerfis() {
        return ResponseEntity.ok(listaPerfisUseCase.execute());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAG')")
    public ResponseEntity<Perfil> criaPerfil(@Valid @RequestBody PerfilDto perfil) {
        return ResponseEntity.ok(criaPerfilUseCase.executa(perfil));
    }
}
