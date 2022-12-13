package com.example.final_project.configures;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtTokenUtils {
    public static final Long ACCESS_TOKEN_VALIDITY_SECONDS = 60 * 60 * 1000L;

    public String getUsernameFromToken(String token, PublicKey publicKey) {
        return getClaimFromToken(token, Claims::getSubject, publicKey);
    }

    public Date getExpirationDateFromToken(String token, PublicKey publicKey) {
        return getClaimFromToken(token, Claims::getExpiration, publicKey);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver, PublicKey publicKey) {
        return claimsResolver.apply(getAllClaimsFromToken(token, publicKey));
    }

    public Claims getAllClaimsFromToken(String token, PublicKey publicKey) {
        return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();
    }

    public boolean isTokenExpired(String token, PublicKey publicKey) {
        return getExpirationDateFromToken(token, publicKey).before(new Date());
    }

    public String generateToken(User user, PrivateKey privateKey) {
        return doGenerateToken(user.getUsername(), user, privateKey);
    }

    public String doGenerateToken(String subject, User user, PrivateKey privateKey) {
        Claims claims = Jwts.claims().setSubject(subject);
        claims.get("scope", SimpleGrantedAuthority.class);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("Nguyễn Quang Thông")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS))
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails, PublicKey publicKey) {
        String username = getUsernameFromToken(token, publicKey);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token, publicKey);
    }

    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
