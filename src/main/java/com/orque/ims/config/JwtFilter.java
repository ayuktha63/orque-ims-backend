package com.orque.ims.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                String username = jwtUtil.extractUsername(token);
                String role = jwtUtil.extractRole(token);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    // This creates ROLE_ADMIN
                    SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + role);

                    System.out.println("AUTHORIZING: User " + username + " with " + authority.getAuthority());

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(username, null, List.of(authority));

                    authToken.setDetails(new org.springframework.security.web.authentication.WebAuthenticationDetailsSource()
                            .buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            } catch (Exception e) {
                System.err.println("JWT Filter Error: " + e.getMessage());
            }
        }
        filterChain.doFilter(request, response);
    }
}