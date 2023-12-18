///*
//package utility;
//
//import com.auth0.jwt.JWT;
//import com.auth0.jwt.JWTVerifier;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.exceptions.JWTVerificationException;
//import com.auth0.jwt.interfaces.DecodedJWT;
//import com.socialmedia.exception.ErrorType;
//import com.socialmedia.exception.UserManagerException;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//import java.util.Optional;
//
//@Component
//public class JWTTokenManager {
//
//    @Value("${jwt.secretKey}")
//    private String secretKey;
//
//    @Value("${jwt.issuer}")
//    private String issuer;
//
//    private final Long expTime = 1000L * 60 * 5;
//
//    public Optional<String> createToken(Long id){
//        String token = null;
//        try {
//            token = JWT.create()
//                    .withAudience()
//                    .withClaim("id", id)   // buraya istediğin herşeyi ve birden çok şekilde tutabilirsin.
//                    .withIssuer(issuer)   // token oluşturan
//                    .withIssuedAt(new Date(System.currentTimeMillis()))   // instant da kabul ediyor. // token oluşturma zamanı
//                    .withExpiresAt(new Date(System.currentTimeMillis() + expTime))
//                    .sign(Algorithm.HMAC512(secretKey));
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//        return Optional.ofNullable(token);
//    }
//
//    public Optional<Long> getIdFromToken(String token){
//        Algorithm algorithm = Algorithm.HMAC512(secretKey);
//        JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build();
//        DecodedJWT decodedJWT = null;
//        try {
//            decodedJWT = verifier.verify(token);
//        } catch (JWTVerificationException e) {
//            throw new UserManagerException(ErrorType.INVALID_TOKEN);
//        }
////        if (decodedJWT == null) throw new UserManagerException(ErrorType.INVALID_TOKEN);
//        Long id = decodedJWT.getClaim("id").asLong(); //claim içindeki valuenun tipini as ile yazıyoruz.
//        return Optional.of(id);
//    }
//
//    public Optional<String> getRoleFromToken(String token){
//        Algorithm algorithm = Algorithm.HMAC512(secretKey);
//        JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build();
//        DecodedJWT decodedJWT = verifier.verify(token);
//        if (decodedJWT == null) throw new UserManagerException(ErrorType.INVALID_TOKEN);
//        String role = decodedJWT.getClaim("role").asString(); //claim içindeki valuenun tipini as ile yazıyoruz.
//        return Optional.of(role);
//    }
//
//}
//*/
