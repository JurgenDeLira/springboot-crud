package com.jorge.springboot.app.springboot_crud.security.filter;

import com.jorge.springboot.app.springboot_crud.entities.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import tools.jackson.core.exc.StreamReadException;
import tools.jackson.databind.DatabindException;
import tools.jackson.databind.ObjectMapper;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    private static final SecretKey SECRET_KEY = Jwts.SIG.HS256.key().build();

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        User user = null;
        String username = null;
        String password = null;

        try {
            user = new ObjectMapper().readValue(request.getInputStream(), User.class);
            username = user.getUsername();
            password = user.getPassword();
        } catch (StreamReadException e){
            e.printStackTrace();
        } catch (DatabindException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
                password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult)
            throws IOException, ServletException {

        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authResult.getPrincipal();
        String username = user.getUsername();
        String token = Jwts.builder()
                .subject(username)
                .signWith(SECRET_KEY)
                .compact();

        response.addHeader("Authorization", "Bearer " + token);

        Map<String, String> body = new HashMap<>();
        body.put("token", token);
        body.put("username", username);
        body.put("message", String.format("Hola %s has iniciado sesión con éxito!", username));

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setContentType("application/json");
        response.setStatus(200);
    }
}
