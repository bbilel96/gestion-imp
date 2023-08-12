package tn.iit.service.micro.app.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.iit.service.micro.app.repository.UtilisateurRepository;
import tn.iit.service.micro.app.service.AuthService;
import tn.iit.service.micro.dto.personne.Token;
import tn.iit.service.micro.dto.personne.UtilisateurDto;
import tn.iit.service.micro.request.personne.UtilisateurRequest;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final ModelMapper modelMapper;
    private final AuthService authService;


    private final UtilisateurRepository utilisateurRepository;

    @PostMapping("/validateToken")
    public ResponseEntity<Token> validateToken(@RequestParam String token) {
        log.info("Trying to validate token {}", token);
        return ResponseEntity.ok(authService.validateToken(token));
    }

    @PostMapping("/signIn")
    public ResponseEntity<Token> signIn(@RequestBody UtilisateurRequest utilisateurRequest) {
        log.info("Trying to login {}", utilisateurRequest);
        return ResponseEntity.ok(authService.signIn(utilisateurRequest));
    }
}
