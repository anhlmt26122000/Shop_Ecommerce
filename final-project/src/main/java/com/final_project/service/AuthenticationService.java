package com.final_project.service;

import com.final_project.dto.request.AuthenticationRequest;
import com.final_project.dto.request.IntrospectRequest;
import com.final_project.dto.request.LogoutRequest;
import com.final_project.dto.response.AuthenticationResponse;
import com.final_project.dto.response.IntrospectResponse;
import com.final_project.entity.InvalidatedToken;
import com.final_project.entity.User;
import com.final_project.exception.AppException;
import com.final_project.exception.ErrorCode;
import com.final_project.repository.InvalidatedTokenRepository;
import com.final_project.repository.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
public class AuthenticationService {
    UserRepository userRepository;
    InvalidatedTokenRepository invalidatedTokenRepository;
    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNING_KEY;

    // kiem tra token co hop le hay khong
    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();
        boolean isValid = true;
        try {
            verifyToken(token);
        }catch (AppException e){
            isValid=false;
        }
        return IntrospectResponse.builder()
                .valid(isValid)
                .build();
    }

    // authen username, password
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(5);
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        var token = generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    //logout , save invalid token
    public void logout(LogoutRequest request) throws ParseException, JOSEException {
        var signToken = verifyToken(request.getToken());

        String jit=signToken.getJWTClaimsSet().getJWTID();
        Date expiryTime = signToken.getJWTClaimsSet().getExpirationTime();

        InvalidatedToken invalidatedToken = InvalidatedToken.builder()
                .id(jit)
                .expiryTime(expiryTime)
                .build();

        invalidatedTokenRepository.save(invalidatedToken);
    }

    private SignedJWT verifyToken(String token) throws JOSEException, ParseException {
        JWSVerifier verifier = new MACVerifier(SIGNING_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        var verified = signedJWT.verify(verifier);

        if(!(verified && expirationTime.after(new Date())))
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        if(invalidatedTokenRepository
                .existsById(signedJWT.getJWTClaimsSet().getJWTID())){
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        return signedJWT;
    }

    // tao token
    private String generateToken(User user) {
        //Header ( thuat toan ma hoa
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        //Thong tin payload
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("final_project")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(24, ChronoUnit.HOURS).toEpochMilli()
                ))
                .jwtID(UUID.randomUUID().toString())
                .claim("scope", buildScope(user))
                .build();
        Payload payload = new Payload(claimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);
        //Hash cua header+Payload
        try {
            jwsObject.sign(new MACSigner(SIGNING_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Error signing token", e);
            throw new RuntimeException(e);
        }
    }

    // tao scope
    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");


        if (!CollectionUtils.isEmpty(user.getRoles())) {
            user.getRoles().forEach(role -> {
                        stringJoiner.add("ROLE_" + role.getName());
                        if(!CollectionUtils.isEmpty(role.getPermissions())) {
                            role.getPermissions().forEach(permission -> stringJoiner.add(permission.getName()));
                        }
                    }
            );
        }
        return stringJoiner.toString();
    }
}
