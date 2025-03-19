package com.final_project.service;

import com.final_project.dto.request.AuthenticationRequest;
import com.final_project.dto.request.IntrospectRequest;
import com.final_project.dto.response.AuthenticationResponse;
import com.final_project.dto.response.IntrospectResponse;
import com.final_project.entity.User;
import com.final_project.exception.AppException;
import com.final_project.exception.ErrorCode;
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
import java.util.Date;
import java.util.StringJoiner;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true,level = lombok.AccessLevel.PRIVATE)
public class AuthenticationService {
    UserRepository userRepository;
    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNING_KEY ;

    // kiem tra token co hop le hay khong
    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();
        JWSVerifier verifier=new MACVerifier(SIGNING_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);
        var verified= signedJWT.verify(verifier);

        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        return IntrospectResponse.builder()
                .valid(verified && expirationTime.after(new Date()))
                .build();
    }

    // authen username, password
    public AuthenticationResponse authenticate(AuthenticationRequest request){
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(()->new AppException(ErrorCode.USER_NOT_EXISTED));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(5);
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if(!authenticated){
            throw new AppException(ErrorCode.WRONG_PASSWORD);
        }
        var token = generateToken(user);
        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    // tao token
    private String generateToken(User user){
        //Header ( thuat toan ma hoa
        JWSHeader header= new JWSHeader(JWSAlgorithm.HS512);
        //Thong tin payload
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("final_project")
                .issueTime(new Date())
                .expirationTime(new Date(System.currentTimeMillis()+86400000))
                .claim("scope",buildScope(user))
                .build();
        Payload payload = new Payload(claimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header,payload);
        //Hash cua header+Payload
        try {
            jwsObject.sign(new MACSigner(SIGNING_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Error signing token",e);
            throw new RuntimeException(e);
        }
    }

    // tao scope
    private String buildScope(User user){
        StringJoiner stringJoiner = new StringJoiner(" ");
        if(!CollectionUtils.isEmpty(user.getRoles())){
            //user.getRoles().forEach(stringJoiner::add);
        }
        return stringJoiner.toString();
    }
}
