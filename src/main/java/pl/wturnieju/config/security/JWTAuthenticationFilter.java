package pl.wturnieju.config.security;

import static pl.wturnieju.config.security.SecurityConstants.EXPIRATION_TIME;
import static pl.wturnieju.config.security.SecurityConstants.HEADER_STRING;
import static pl.wturnieju.config.security.SecurityConstants.SECRET;
import static pl.wturnieju.config.security.SecurityConstants.TOKEN_PREFIX;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import pl.wturnieju.controller.dto.auth.LoginDTO;
import pl.wturnieju.model.User;

@AllArgsConstructor
@Log4j2
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request, HttpServletResponse response) {
        try {
            var credentials = new ObjectMapper().readValue(request.getInputStream(), LoginDTO.class);

            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    credentials.getUsername(),
                    credentials.getPassword(),
                    new ArrayList<>()
            ));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        String token = JWT.create()
                .withSubject(((User) authResult.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SECRET.getBytes()));
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
        response.setContentType("application/json; charset=utf-8");
        try {
            response.getWriter().write(new ObjectMapper().writeValueAsString(authResult.getPrincipal()));
            log.info(String.format("User [%s] logged in.", ((User) authResult.getPrincipal()).getUsername()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
