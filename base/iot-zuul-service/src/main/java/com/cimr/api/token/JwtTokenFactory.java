package com.cimr.api.token;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Factory class that should be always used to create {@link JwtToken}.
 * 
 * @author vladimir.stankovic
 *
 * May 31, 2016
 */
@Component
public class JwtTokenFactory {
    private final JwtSettings settings;

    @Autowired
    public JwtTokenFactory(JwtSettings settings) {
        this.settings = settings;
    }
    
    //解析Token，同时也能验证Token，当验证失败返回null
    public Map<String, Object> parserJavaWebToken(String jwt) {
        try {
        	Key pk = RsaUtil.getPublicKey(settings.getPublicKey());
            Map<String, Object> jwtClaims =
                    Jwts.parser().setSigningKey(pk).parseClaimsJws(jwt).getBody();
            return jwtClaims;
        } catch (Exception e) {
            return null;
        }
    }
    
}
