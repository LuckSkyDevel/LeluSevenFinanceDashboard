package com.finance.lululeleseven.perfil.infrastructure;

import com.finance.lululeleseven.perfil.application.CriaPerfilUseCase;
import com.finance.lululeleseven.perfil.application.ListaPerfisPorUsuarioUseCase;
import com.finance.lululeleseven.perfil.application.ListaPerfisUseCase;
import com.finance.lululeleseven.perfil.application.RecuperaPerfilUseCase;
import com.finance.lululeleseven.perfil.application.dto.PerfilDto;
import com.finance.lululeleseven.perfil.domain.Perfil;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
    private final ListaPerfisPorUsuarioUseCase listaPerfisPorUsuarioUseCase;
    private final ListaPerfisUseCase listaPerfisUseCase;

    @GetMapping("/{codPerfil}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAG')")
    public ResponseEntity<Perfil> recuperaPerfilPorCodigoPerfil(@PathVariable Long codPerfil) {
        return ResponseEntity.ok(recuperaPerfilUseCase.execute(codPerfil));
    }

    @GetMapping("/{nomPerfil")
    @PreAuthorize("hasAnyRole('ADMIN','MANAG')")
    public ResponseEntity<Perfil> recuperarPerfilPorNomePerfil(@PathVariable String nomPerfil) {
        return ResponseEntity.ok(recuperaPerfilUseCase.execute(nomPerfil));
    }

    @GetMapping
    public ResponseEntity<List<Perfil>> listaTodosPerfis() {
        return ResponseEntity.ok(listaPerfisUseCase.execute());
    }

    @GetMapping("/{codUsuario}")
    @PreAuthorize("hasAnyRole('ADMIN','MANAG')")
    public ResponseEntity<List<Perfil>> recuperaPerfisPorCodUsuario(@PathVariable Long codUsuario) {
        return ResponseEntity.ok(listaPerfisPorUsuarioUseCase.execute(codUsuario));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','MANAG')")
    public ResponseEntity<Perfil> criaPerfil(@Valid @RequestBody PerfilDto perfil) {
        return ResponseEntity.ok(criaPerfilUseCase.executa(perfil));
    }
}
