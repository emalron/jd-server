package service;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import model.Connector;

public class JWT {
    public String generate() {
        Map<String, Object> headers = new HashMap<>();
        headers.put("typ", "JWT");
        headers.put("alg", "HS256");

        Map<String, Object> payloads = new HashMap<>();
        Long expiredTime = 1000*60*60l; // 1 hour
        Date now = new Date();
        now.setTime(now.getTime() + expiredTime);
        payloads.put("exp", now);
        payloads.put("data", "can you see me?");

        Connector c = Connector.getInstance();
        String key = c.getJWT();

        String jwt = Jwts.builder()
        .setHeader(headers)
        .setClaims(payloads)
        .signWith(SignatureAlgorithm.HS256, key.getBytes())
        .compact();

        return jwt;
    }

    public int verify(String jwt){
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey("secret".getBytes("utf-8")).parseClaimsJws(jwt);

        }
        catch(SignatureException e) {
            return -1;
        }
        catch(UnsupportedEncodingException e) {
            return -2;
        }
        

        return 0;

    }
    
}