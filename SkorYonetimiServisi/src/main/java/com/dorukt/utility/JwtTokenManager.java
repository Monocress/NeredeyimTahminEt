package com.dorukt.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.dorukt.exception.ErrorType;
import com.dorukt.exception.SkorManagerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class JwtTokenManager {
    @Value("${jwt.secretKey}")
    String secretKey;
    @Value("${jwt.issuer}")
    String issuer;
    @Value("${jwt.audience}")
    String audience;

    public Optional<String> createToken(Long id, Long skor) {
        Date date = new Date(System.currentTimeMillis() + 1000 * 60 * 5);
        String token = null;
        try {
            token = JWT.create()
                    .withAudience(audience)
                    .withIssuer(issuer)
                    .withClaim("id", id)
                    .withClaim("skor", skor)
                    .withIssuedAt(new Date())
                    .withExpiresAt(date)
                    .sign(Algorithm.HMAC512(secretKey));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return Optional.ofNullable(token);
    }


    public Optional<Long> getIdFromUserToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).withAudience(audience).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            if (decodedJWT == null)
                throw new SkorManagerException(ErrorType.INVALID_TOKEN);
            Long id = decodedJWT.getClaim("id").asLong();
            String username = decodedJWT.getClaim("kullaniciAdi").asString();
            if(username.isEmpty())
                throw new SkorManagerException(ErrorType.INVALID_TOKEN);
            return Optional.of(id);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (JWTVerificationException e) {
            e.printStackTrace();
        } catch (SkorManagerException e) {
            System.out.println("Yanlış token girildi.");
        }
        return Optional.empty();
    }

    public Optional<Long> getSkorFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).withAudience(audience).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            if (decodedJWT == null)
                throw new SkorManagerException(ErrorType.INVALID_TOKEN);
            Long id = decodedJWT.getClaim("id").asLong();
            if(id==null)
                throw new SkorManagerException(ErrorType.INVALID_TOKEN);
            Long skor = decodedJWT.getClaim("skor").asLong();
            return Optional.of(skor);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (JWTVerificationException e) {
            e.printStackTrace();
        } catch (SkorManagerException e) {
            System.out.println("Yanlış token girildi.");
        }
        return Optional.empty();
    }

}
