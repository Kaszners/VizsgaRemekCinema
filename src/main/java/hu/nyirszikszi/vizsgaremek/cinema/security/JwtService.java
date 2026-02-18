package hu.nyirszikszi.vizsgaremek.cinema.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {



    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);


    public String generateToken(String username, String role){

        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(
                        new Date(System.currentTimeMillis()+1000*60*60)
                )
                .signWith(secretKey)
                .compact();
    }


    public String extractUserName(String token){
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseEncryptedClaims(token)
                .getPayload()
                .getSubject();
    }


}
