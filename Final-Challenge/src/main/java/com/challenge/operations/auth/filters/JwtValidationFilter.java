package com.challenge.operations.auth.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.challenge.operations.auth.TokenJwtConfing.*;

public class JwtValidationFilter extends BasicAuthenticationFilter{

    public JwtValidationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        //TODO Auto-generated constructor stub
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String header = request.getHeader(HEADER_AUTHORIZATION);

        if(header == null || !header.startsWith(PREFIX_TOKEN)) {
            chain.doFilter(request, response);
            return;
        }

        String token = header.replace(PREFIX_TOKEN, "");
        System.out.println("token ---->" + token);
        // Reemplazar caracteres URL-safe para Base64 estándar
        String base64Token = token.replace('-', '+').replace('_', '/');
        int paddingRequired = 4 - (base64Token.length() % 4);
        if (paddingRequired < 4) {
            base64Token += "=".repeat(paddingRequired);
        }
        byte[] tokenDecodedBytes = Base64.getDecoder().decode(base64Token);
        System.out.println("tokenDB ---->" + tokenDecodedBytes);
        String tokenDecoded = new String(tokenDecodedBytes);
        
        String[] tokenArr = tokenDecoded.split("\\.");
        String secret = tokenArr[0];
        String username = tokenArr[1];

        if(SECRET_KEY.equals(secret) ) {
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            chain.doFilter(request, response);
        } else {
            Map<String, String> body = new HashMap<>();
            body.put("message", "invalid Token!");

            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            response.setStatus(403);
            response.setContentType("application/JSON");
        }
    }
    
}
