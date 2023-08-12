package tn.iit.service.micro.app.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tn.iit.service.micro.app.model.Utilisateur;
import tn.iit.service.micro.app.service.implementation.UtilisateurServiceImp;
import tn.iit.service.micro.dto.personne.UtilisateurDto;
import tn.iit.service.micro.request.action.Create;
import tn.iit.service.micro.request.action.Update;
import tn.iit.service.micro.request.personne.UtilisateurRequest;
import tn.iit.service.micro.utilclass.ResponseMessage;


@RestController
@RequestMapping("utilisateur")
@RequiredArgsConstructor
public class UtilisateurController {
    private final UtilisateurServiceImp utilisateurServiceImp;
    private final ModelMapper modelMapper;


    @GetMapping("/{id}")
    public ResponseEntity<UtilisateurDto> findById(@PathVariable String id) {
        return new ResponseEntity<>(modelMapper.map(utilisateurServiceImp.getById(id), UtilisateurDto.class), HttpStatus.OK);
    }

    @GetMapping("page/{page}/size/{size}")
    public ResponseEntity<Page<UtilisateurDto>> findAll(@PathVariable int page, @PathVariable int size) {
        return new ResponseEntity<>(
                this.utilisateurServiceImp.getAll(page, size)
                        .map(utilisateur -> modelMapper.map(utilisateur, UtilisateurDto.class)),
                HttpStatus.OK
        );
    }
    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> updateUtilisateur(@PathVariable String id, @RequestBody @Validated(Update.class) UtilisateurRequest utilisateurRequest) {
        return new ResponseEntity<>(
                this.utilisateurServiceImp.updateUtilisateur(
                        modelMapper.map(utilisateurRequest, Utilisateur.class),
                        id
                ),
                HttpStatus.OK
        );
    }

}

