package com.example.springconnectmysql.service;

import com.example.springconnectmysql.dto.request.AuthenticationRequest;
import com.example.springconnectmysql.dto.request.IntrospectRequest;
import com.example.springconnectmysql.dto.respone.AuthenticationRespone;
import com.example.springconnectmysql.dto.respone.IntrospectRespone;
import com.example.springconnectmysql.entity.User;
import com.example.springconnectmysql.exception.AppException;
import com.example.springconnectmysql.exception.ErrorCode;
import com.example.springconnectmysql.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;
    @NonFinal
    @Value("${jwt.signerKey}")
    protected  String  SIGNED_KEY;

    public AuthenticationRespone authenticate(AuthenticationRequest request){
        var user = userRepository.findByUserName(request.getUserName())
                .orElseThrow(()-> new AppException(ErrorCode.IDUSER_NOT_EXIST));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated =passwordEncoder.matches(request.getPassword(), user.getPassword());
        if(!authenticated)
            new AppException(ErrorCode.UNAUTHENTICATED);
        var token = generateToken(request.getUserName());
        return  AuthenticationRespone.builder()
                .token(token)
                .authenticated(true)
                .build();

    }
    private String generateToken(String userName){
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(userName)
                .issuer("pornhub.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .claim("customClaim", "Custom")
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader,payload);
        try {
            jwsObject.sign(new MACSigner(SIGNED_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token", e);
            throw new RuntimeException(e);
        }
    }
    public IntrospectRespone introspect(IntrospectRequest request){
        try {
            JWSVerifier verifier = new MACVerifier(SIGNED_KEY.getBytes());
            SignedJWT signedJWT = SignedJWT.parse(request.getToken());
            Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            boolean verified = signedJWT.verify(verifier);

            return IntrospectRespone.builder()
                    .valid(verified && expiryTime.after(new Date()))
                    .build();
        } catch (Exception e) {
            log.error("Token verification failed", e);
            return IntrospectRespone.builder()
                    .valid(false)
                    .build();
        }
    }

}
