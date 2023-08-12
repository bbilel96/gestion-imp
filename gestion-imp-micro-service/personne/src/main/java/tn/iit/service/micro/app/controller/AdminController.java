package tn.iit.service.micro.app.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tn.iit.service.micro.app.model.Admin;
import tn.iit.service.micro.app.model.Enseignant;
import tn.iit.service.micro.app.service.implementation.AdminServiceImp;
import tn.iit.service.micro.dto.personne.AdminDto;
import tn.iit.service.micro.dto.personne.AgentDto;
import tn.iit.service.micro.request.action.Create;
import tn.iit.service.micro.request.personne.AdminRequest;
import tn.iit.service.micro.request.personne.EnseignantRequest;
import tn.iit.service.micro.utilclass.ResponseMessage;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminServiceImp adminServiceImp;
    private final ModelMapper modelMapper;

    @PostMapping("")
    public ResponseEntity<ResponseMessage> createAdmin(@RequestBody @Validated(Create.class) AdminRequest adminRequest) {
        return new ResponseEntity<>(adminServiceImp.createUtilisateur(modelMapper.map(adminRequest, Admin.class)), HttpStatus.OK);
    }

    @GetMapping("/{page}/{size}")
    public ResponseEntity<Page<AdminDto>> findAll(@PathVariable int page, @PathVariable int size) {

        return new ResponseEntity<>(
                this.adminServiceImp.getAll(page, size)
                        .map(admin -> modelMapper.map(admin, AdminDto.class)),
                HttpStatus.OK
        );
    }
    @GetMapping("/{id}")
    public ResponseEntity<AdminDto> findById(@PathVariable String id) {
        return new ResponseEntity<>(modelMapper.map(adminServiceImp.getById(id), AdminDto.class), HttpStatus.OK);
    }
}
