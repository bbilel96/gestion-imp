package tn.iit.service.micro.app.service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.iit.service.micro.app.model.Utilisateur;
import tn.iit.service.micro.app.repository.UtilisateurRepository;
import tn.iit.service.micro.dto.personne.Token;
import tn.iit.service.micro.dto.personne.UtilisateurDto;
import tn.iit.service.micro.exception.classexception.BadRequestException;
import tn.iit.service.micro.exception.classexception.ObjectNotFound;
import tn.iit.service.micro.request.personne.UtilisateurRequest;

import java.nio.CharBuffer;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {
    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;
    private final UtilisateurRepository utilisateurRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    public Token signIn(UtilisateurRequest utilisateurRequest) {
        var user = utilisateurRepository.findByEmailAndPassword(utilisateurRequest.getEmail(), utilisateurRequest.getPassword())
                .orElseThrow(() -> new ObjectNotFound("User not found"));

        if (utilisateurRequest.getPassword().equals(user.getPassword())) {

            return  new Token(modelMapper.map(user, UtilisateurDto.class), createToken(user));

        }

        throw new BadRequestException("Invalid password");
    }


    public Token validateToken(String token){
        Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());

        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String id = decodedJWT.getSubject();
        String roles = decodedJWT.getClaim("roles").toString();
        Utilisateur userOptional = utilisateurRepository.findById(id).orElseThrow(()->new ObjectNotFound("Utilisateur n'existe pas."));


        return  new Token(modelMapper.map(userOptional, UtilisateurDto.class), createToken(userOptional));
    }
    private String createToken(Utilisateur user) {
        Algorithm algorithm = Algorithm.HMAC256(secretKey.getBytes());
        return JWT
                .create()
                .withSubject(user.getId())
                .withExpiresAt(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                .withClaim("roles", user.getTypeUser().toString())
                .sign(algorithm);
    }

}
