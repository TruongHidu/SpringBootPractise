package com.example.springconnectmysql.service;

import com.example.springconnectmysql.dto.request.AuthenticationRequest;
import com.example.springconnectmysql.dto.request.IntrospectRequest;
import com.example.springconnectmysql.dto.request.LogoutRequest;
import com.example.springconnectmysql.dto.respone.AuthenticationRespone;
import com.example.springconnectmysql.dto.respone.IntrospectRespone;
import com.example.springconnectmysql.entity.InvalidatedToken;
import com.example.springconnectmysql.entity.User;
import com.example.springconnectmysql.exception.AppException;
import com.example.springconnectmysql.exception.ErrorCode;
import com.example.springconnectmysql.repository.InvalidatedTokenRepository;
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
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;
    InvalidatedTokenRepository invalidatedTokenRepository;
    @NonFinal
    @Value("${jwt.signerKey}")
    protected  String  SIGNED_KEY;

    public AuthenticationRespone authenticate(AuthenticationRequest request){
        var user = userRepository.findByUserName(request.getUserName())
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXIST));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated =passwordEncoder.matches(request.getPassword(), user.getPassword());
        if(!authenticated)
            new AppException(ErrorCode.UNAUTHENTICATED);
        var token = generateToken(user);

        return  AuthenticationRespone.builder()
                .token(token)
                .authenticated(true)
                .build();

    }

    public void logout(LogoutRequest request) throws JOSEException, ParseException {

        var signedToken = verifyToken(request.getToken());

        String jit = signedToken.getJWTClaimsSet().getJWTID();

        Date expiryTime = signedToken.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jit)
                .expiryTime(expiryTime)
                .build();
        invalidatedTokenRepository.save(invalidatedToken);

    }
    private SignedJWT verifyToken(String token)
        throws JOSEException, ParseException {

        JWSVerifier verifier = new MACVerifier(SIGNED_KEY.getBytes());

        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if(!(verified && expiryTime.after(new Date())))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        if(invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID()))
            throw new AppException(ErrorCode.UNAUTHENTICATED);

        return signedJWT;
    }
    private String generateToken(User user){
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUserName())
                .issuer("pornhub.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.HOURS).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope",  buildScope(user))
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
    public IntrospectRespone introspect(IntrospectRequest request)
            throws JOSEException, ParseException {

        var token = request.getToken();

        boolean isValid = true;

        try {
            verifyToken(token);
        }catch (AppException e){
            isValid = false;
        }

        return IntrospectRespone.builder()
                .valid(isValid)
                .build();

    }
    private String buildScope(User user){
        StringJoiner stringJoiner = new StringJoiner(" ");
        if (!CollectionUtils.isEmpty(user.getRoles()))
            user.getRoles().forEach(role ->{
                    stringJoiner.add("ROLE_" + role.getName());
                    if(!CollectionUtils.isEmpty(role.getPermissions()))
                        role.getPermissions()
                                .forEach(permission -> stringJoiner.add(permission.getName()));

    });

        return  stringJoiner.toString();
    }

}
