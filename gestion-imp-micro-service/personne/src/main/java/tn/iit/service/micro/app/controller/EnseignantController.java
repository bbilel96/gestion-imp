package tn.iit.service.micro.app.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.*;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tn.iit.service.micro.app.model.Enseignant;
import tn.iit.service.micro.app.service.implementation.EnseignantServiceImp;
import tn.iit.service.micro.dto.personne.EnseignantDto;
import tn.iit.service.micro.mapper.DateMapper;
import tn.iit.service.micro.request.action.Create;
import tn.iit.service.micro.request.personne.EnseignantRequest;
import tn.iit.service.micro.utilclass.ResponseMessage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("enseignant")
@RequiredArgsConstructor
public class EnseignantController {
    private final EnseignantServiceImp enseignantServiceImp;
    private final ModelMapper modelMapper;


    @PostMapping("")
    public ResponseEntity<ResponseMessage> createEnseignant(@RequestBody @Validated(Create.class) EnseignantRequest enseignantRequest) {

        return new ResponseEntity<>(enseignantServiceImp.createUtilisateur(modelMapper.map(enseignantRequest, Enseignant.class)), HttpStatus.OK);
    }

    @GetMapping("/page/{page}/size/{size}")
    public ResponseEntity<Page<EnseignantDto>> findAll(@PathVariable int page, @PathVariable int size) {
        return new ResponseEntity<>(
                this.enseignantServiceImp.getAll(page, size)
                        .map(enseignant -> modelMapper.map(enseignant, EnseignantDto.class)),
                HttpStatus.OK
        );
    }
    @GetMapping("")
    public ResponseEntity<List<EnseignantDto>> findAllWithoutPage() {
        return new ResponseEntity<>(
                this.enseignantServiceImp.getAllWithoutPage()
                        .stream()
                        .map(enseignant -> modelMapper.map(enseignant, EnseignantDto.class))
                        .toList(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnseignantDto> findById(@PathVariable String id) {
        return new ResponseEntity<>(modelMapper.map(enseignantServiceImp.getById(id), EnseignantDto.class), HttpStatus.OK);
    }

}
