package tn.iit.service.micro.app.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import tn.iit.service.micro.app.model.Classe;
import tn.iit.service.micro.app.model.Matiere;
import tn.iit.service.micro.app.repository.ClasseRepository;
import tn.iit.service.micro.app.service.implementation.ClasseServiceImp;
import tn.iit.service.micro.dto.classe.ClasseDto;
import tn.iit.service.micro.request.action.Create;
import tn.iit.service.micro.request.action.Update;
import tn.iit.service.micro.request.classe.ClasseRequest;
import tn.iit.service.micro.request.classe.MatiereRequest;
import tn.iit.service.micro.utilclass.ResponseMessage;

import javax.xml.transform.OutputKeys;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class ClasseController {
    private final ClasseServiceImp classeServiceImp;
    private final ModelMapper modelMapper;
    private final ClasseRepository classeRepository;

    @GetMapping("matiere/{id}/page/{page}/size/{size}")
    public ResponseEntity<Page<ClasseDto>> findAllByMatiereId(@PathVariable String id, @PathVariable int page, @PathVariable int size) {
        modelMapper.typeMap(Classe.class, ClasseDto.class)
                .addMappings(m -> m.skip(ClasseDto::setMatieres));
        return new ResponseEntity<>(
                this.classeServiceImp.findAllByMatiereId(page, size, id)
                        .map(classe -> modelMapper.map(classe, ClasseDto.class)),
                HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<ResponseMessage> createClasse(@RequestBody @Validated(Create.class) ClasseRequest classeRequest) {
        return new ResponseEntity<>(
                this.classeServiceImp.createClasse(
                        modelMapper.map(classeRequest, Classe.class)
                ),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteClasse(@PathVariable String id) {
        return new ResponseEntity<>(
                this.classeServiceImp.deleteClasse(id),
                HttpStatus.OK
        );
    }

    @GetMapping("/page/{page}/size/{size}")
    public ResponseEntity<Page<ClasseDto>> getAll(@PathVariable int page, @PathVariable int size) {
        modelMapper.typeMap(Classe.class, ClasseDto.class)
                .addMappings(m -> m.skip(ClasseDto::setMatieres));
        return new ResponseEntity<>(
                this.classeServiceImp.findAll(page, size)
                        .map(classe -> modelMapper.map(classe, ClasseDto.class))
                , HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClasseDto> findById(@PathVariable String id) {
        modelMapper.typeMap(Classe.class, ClasseDto.class)
                .addMappings(m -> m.skip(ClasseDto::setMatieres));
        return new ResponseEntity<>(
                modelMapper.map(
                        this.classeServiceImp.getById(id), ClasseDto.class)
                , HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> updateClasse(@PathVariable String id, @RequestBody @Validated(Update.class) ClasseRequest classeRequest) {
        return new ResponseEntity<>(this.classeServiceImp.updateClasse(
                modelMapper.map(classeRequest, Classe.class),
                id
        ),
                HttpStatus.OK);
    }
    @GetMapping("/matiere/{matiereId}/list")
    public ResponseEntity<List<ClasseDto>> getAllWithoutData(@PathVariable String matiereId) {
        modelMapper.typeMap(Classe.class, ClasseDto.class)
                .addMappings(m -> m.skip(ClasseDto::setMatieres));
        return new ResponseEntity<>(
                        this.classeServiceImp.findAllByMatiereIdWithoutPage(matiereId)
                                .stream()
                                .map(classe -> this.modelMapper.map(classe, ClasseDto.class))
                                .collect(Collectors.toList())
                , HttpStatus.OK);
    }
    @PostMapping("affect_matiere/{classeId}")
    public ResponseEntity<ResponseMessage> affectMatiere (@RequestBody List<MatiereRequest> matiereRequest, @PathVariable String classeId){
        return new ResponseEntity<>(
                        this.classeServiceImp.affectMatiere(classeId, matiereRequest
                                .stream()
                                .map(matiereRequest1 -> modelMapper.map(matiereRequest1, Matiere.class))
                                .collect(Collectors.toList())),
                        HttpStatus.OK

        );


    }
    @GetMapping("")
    public ResponseEntity<List<ClasseDto>> getAllWithoutPage (){
        modelMapper.typeMap(Classe.class, ClasseDto.class)
                .addMappings(m -> m.skip(ClasseDto::setMatieres));
        return new ResponseEntity<>(this.classeServiceImp.findAllWithoutPage()
                .stream()
                .map(classe -> modelMapper.map(classe, ClasseDto.class))
                .collect(Collectors.toList())
                ,HttpStatus.OK );

    }

}
