package service;

import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import model.Connector;

public class JWT {
    public String generate(String id) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");

        Map<String, Object> payloads = new HashMap<>();
        payloads.put("id", id);

        Connector c = Connector.getInstance();
        String key = c.getJWT();

        String jwt = Jwts.builder()
        .setHeader(headers)
        .setClaims(payloads)
        .signWith(SignatureAlgorithm.HS256, key.getBytes())
        .compact();

        return jwt;
    }

    public String verify(String jwt) {
        Connector c = Connector.getInstance();
        String key = c.getJWT();

        try {
            Claims claims = Jwts.parser()
            .setSigningKey(key.getBytes())
            .parseClaimsJws(jwt)
            .getBody();

            String id = claims.get("id", String.class);

            return id;
        }
        catch(SignatureException e) {
            return null;
        }
    }
}