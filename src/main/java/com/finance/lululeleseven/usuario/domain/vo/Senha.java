package com.finance.lululeleseven.usuario.domain.vo;

import com.finance.lululeleseven.shared.exception.DomainException;
import org.springframework.security.crypto.password.PasswordEncoder;

public record Senha(String hash) {
    public static Senha criar(String senhaPura, PasswordEncoder encoder) {
        if (senhaPura == null || senhaPura.length() < 8)
            throw new DomainException("Senha deve ter pelo menos 8 caracteres");
        return new Senha(encoder.encode(senhaPura));
    }

    // Cria a partir de hash já existente (vindo do banco)
    public static Senha doBanco(String hash) {
        return new Senha(hash);
    }

    public boolean confere(String senhaPura, PasswordEncoder encoder) {
        return encoder.matches(senhaPura, this.hash);
    }
}
