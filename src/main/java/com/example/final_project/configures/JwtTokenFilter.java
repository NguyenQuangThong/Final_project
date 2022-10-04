package com.example.final_project.configures;

import com.example.final_project.models.Account;
import com.example.final_project.repositories.AccountRepository;
import com.example.final_project.services.implement.AccountService;
import com.example.final_project.services.implement.KeyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    @Autowired
    KeyGenerator keyGenerator;
    @Autowired
    KeyService keyService;
    @Autowired
    JwtTokenUtils jwtTokenUtils;
    PublicKey publicKey;
    PrivateKey privateKey;
    List<Key> keys;
    @Autowired
    AccountRepository accountRepository;
    UserDetails userDetails;
    @Autowired
    AccountService accountService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (keyService.getAllKeyPair().isEmpty()) {
            try {
                keys = keyGenerator.genKeyPair();
                publicKey = (PublicKey) keys.get(0);
                privateKey = (PrivateKey) keys.get(1);
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                keys = keyGenerator.getKeyPair();
                publicKey = (PublicKey) keys.get(0);
                privateKey = (PrivateKey) keys.get(1);
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                throw new RuntimeException(e);
            }
        }
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = header.substring(7);
        Account account = accountRepository.findByUsername(jwtTokenUtils.getUsernameFromToken(token, publicKey));
        userDetails = accountService.loadUserByUsername(account.getUsername());
        if (!jwtTokenUtils.validateToken(token, userDetails, publicKey)) {
            filterChain.doFilter(request, response);
            return;
        }
        User user = (User) accountService.loadUserByUsername(account.getUsername());
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                user, null,
                user.getAuthorities()
        );
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
