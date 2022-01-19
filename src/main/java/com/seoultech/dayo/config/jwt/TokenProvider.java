package com.seoultech.dayo.config.jwt;


import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class TokenProvider {

    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000L * 60 * 60 * 24;
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000L * 60 * 60 * 24 * 30;

    private final Key key;

    public TokenProvider() {
        byte[] decode = Decoders.BASE64.decode(JwtConfig.JWT_SECRET);
        this.key = Keys.hmacShaKeyFor(decode);
    }

    public TokenDto generateToken(String id) {

        long now = (new Date()).getTime();

        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        String accessToken = Jwts.builder()
                .setId(id)
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        Date refreshTokenExpiresIn = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);
        String refreshToken = Jwts.builder()
                .setExpiration(refreshTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        return TokenDto.from(accessToken, refreshToken);
    }

    public String getDataFromToken(String token) {

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.get("jti", String.class);
    }

    public String getTokenInHeader(HttpServletRequest servletRequest) {
        return servletRequest.getHeader("Authorization").substring(7);
    }

}
