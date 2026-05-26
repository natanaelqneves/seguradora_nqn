package com.nqn.segnqn.security;

import com.nqn.segnqn.repository.CorretorRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JwtService jwtService;

    private CorretorRepository corretorRepository;

    public JwtAuthenticationFilter(JwtService jwtService, CorretorRepository corretorRepository) {
        this.jwtService = jwtService;
        this.corretorRepository = corretorRepository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userLogin;

        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        userLogin = jwtService.extrairLogin(jwt);

        if(userLogin != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            var corretor = this.corretorRepository.findByLogin(userLogin).orElse(null);

            if(corretor != null && jwtService.isTokenValido(jwt, corretor) && corretor.isEnabled()) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        corretor,
                        null,
                        corretor.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
