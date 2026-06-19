package com.finance.lululeleseven.shared.infrastructure.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {
    private TokenService tokenService;
    private UserDetailsService userDetailsService;

    public JwtFilter(TokenService tokenService, UserDetailsService userDetailsService) {
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7);

        try {
            if (tokenService.validateToken(token) && SecurityContextHolder.getContext().getAuthentication() == null) {
             Claims claims = tokenService.extractAllClaims(token);
             String username = claims.getSubject();
             UserDetails userDetails = userDetailsService.loadUserByUsername(username);

             var auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

             SecurityContextHolder.getContext().setAuthentication(auth);
             }
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido ou expirado");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
