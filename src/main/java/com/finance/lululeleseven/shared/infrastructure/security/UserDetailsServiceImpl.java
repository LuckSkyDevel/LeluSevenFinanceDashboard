package com.finance.lululeleseven.shared.infrastructure.security;

import com.finance.lululeleseven.usuario.infrastructure.IUsuarioJpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private IUsuarioJpaRepository usuarioRepository;

    public UserDetailsServiceImpl(IUsuarioJpaRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByNomUsuario(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}
