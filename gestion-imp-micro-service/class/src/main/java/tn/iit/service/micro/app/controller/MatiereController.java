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
import tn.iit.service.micro.app.service.implementation.MatiereServiceImp;
import tn.iit.service.micro.dto.classe.MatiereDto;
import tn.iit.service.micro.request.action.Create;
import tn.iit.service.micro.request.action.Update;
import tn.iit.service.micro.request.classe.MatiereRequest;
import tn.iit.service.micro.utilclass.ResponseMessage;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("matiere")
@RequiredArgsConstructor
public class MatiereController {
    private final ModelMapper modelMapper;
    private final MatiereServiceImp matiereServiceImp;

    @PostMapping("")
    public ResponseEntity<ResponseMessage> createMatiere(@RequestBody @Validated(Create.class) MatiereRequest matiereRequest) {
        return new ResponseEntity<>(
                this.matiereServiceImp.createMatiere(
                        modelMapper.map(matiereRequest, Matiere.class)
                ), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteMatiere(@PathVariable String id) {
        return new ResponseEntity<>(
                this.matiereServiceImp.deleteMatiere(id),
                HttpStatus.OK
        );
    }

    @GetMapping("/page/{page}/size/{size}")
    public ResponseEntity<Page<MatiereDto>> getAll(@PathVariable int page, @PathVariable int size) {
        modelMapper.typeMap(Matiere.class, MatiereDto.class)
                .addMappings(m -> m.skip(MatiereDto::setClasses));
        return new ResponseEntity<>(
                this.matiereServiceImp.getAll(page, size)
                        .map(matiere -> modelMapper.map(matiere, MatiereDto.class)),
                HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<MatiereDto> getById(@PathVariable String id) {
        modelMapper.typeMap(Matiere.class, MatiereDto.class)
                .addMappings(m -> m.skip(MatiereDto::setClasses));
        return new ResponseEntity<>(
                modelMapper.map(
                        this.matiereServiceImp.findById(id), MatiereDto.class),
                HttpStatus.OK);
    }

    @GetMapping("enseignant/{id}/page/{page}/size/{size}")
    public ResponseEntity<Page<MatiereDto>> findAllByEnseignantId(@PathVariable String id, @PathVariable int page, @PathVariable int size) {
        modelMapper.typeMap(Matiere.class, MatiereDto.class)
                .addMappings(m -> m.skip(MatiereDto::setClasses));
        return new ResponseEntity<>(this.matiereServiceImp.findAllByEnseignantId(page, size, id)
                .map(matiere -> this.modelMapper.map(matiere, MatiereDto.class)), HttpStatus.OK);
    }

    @PostMapping("{id}/enseignant/{enseignantId}")
    public ResponseEntity<ResponseMessage> affectEnseignantToMatiere(@PathVariable String id, @PathVariable String enseignantId) {
        return new ResponseEntity<>(this.matiereServiceImp.affectEnseignantToMatiere(id, enseignantId), HttpStatus.OK);
    }

    @GetMapping("classe/{id}/page/{page}/size/{size}")
    public ResponseEntity<Page<MatiereDto>> getByClasseId(@PathVariable String id, @PathVariable int page, @PathVariable int size) {
        modelMapper.typeMap(Matiere.class, MatiereDto.class)
                .addMappings(m -> m.skip(MatiereDto::setClasses));
        return new ResponseEntity<>(this.matiereServiceImp.findAllByClasseId(id, page, size)
                .map(matiere -> this.modelMapper.map(matiere, MatiereDto.class)), HttpStatus.OK);
    }

    @GetMapping("classe/{id}")
    public ResponseEntity<List<MatiereDto>> getByClasseIdWithoutPage(@PathVariable String id) {
        modelMapper.typeMap(Matiere.class, MatiereDto.class)
                .addMappings(m -> m.skip(MatiereDto::setClasses));
        return new ResponseEntity<>(
                this.matiereServiceImp.findAllByClasseIdWithoutPage(id)
                        .stream()
                        .map(matiere -> this.modelMapper.map(matiere, MatiereDto.class))
                        .toList(),
                HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<MatiereDto>> getAllWithoutPage() {
        modelMapper.typeMap(Matiere.class, MatiereDto.class)
                .addMappings(m -> m.skip(MatiereDto::setClasses));
        return new ResponseEntity<>(this.matiereServiceImp.getAllWithoutPage()
                .stream()
                .map(matiere -> this.modelMapper.map(matiere, MatiereDto.class))
                .collect(Collectors.toList()),
                HttpStatus.OK
        );
    }
    @GetMapping("enseignant/{id}")
    public ResponseEntity<List<MatiereDto>> getAllWithoutPageByEnseignant(@PathVariable String id){
        modelMapper.typeMap(Matiere.class, MatiereDto.class)
                .addMappings(m -> m.skip(MatiereDto::setClasses));
        return new ResponseEntity<>(this.matiereServiceImp.getAllWithoutPageByEnseignant(id)
                .stream()
                .map(matiere -> modelMapper.map(matiere, MatiereDto.class))
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> updateMatiere(@PathVariable String id, @RequestBody @Validated(Update.class) MatiereRequest matiereRequest) {
        return new ResponseEntity<>(
                this.matiereServiceImp.updateMatiere(
                        id, modelMapper.map(matiereRequest, Matiere.class)
                ), HttpStatus.OK);
    }
    @PostMapping("affect_classe/{id}")
    public ResponseEntity<ResponseMessage> affectClasse(@PathVariable String id, @RequestBody List<Classe> classesId) {
        return new ResponseEntity<>(this.matiereServiceImp.affectClasses(id, classesId), HttpStatus.OK);
    }
    @GetMapping("agent/{agentId}/page/{page}/size/{size}")
    public ResponseEntity<Page<MatiereDto>> getMatiereByAgentId(@PathVariable String agentId, @PathVariable int page, @PathVariable int size){
        modelMapper.typeMap(Matiere.class, MatiereDto.class)
                .addMappings(m -> m.skip(MatiereDto::setClasses));
        return new ResponseEntity<>(this.matiereServiceImp.getMatiereByAgentId(agentId, page, size)
                .map(matiere -> modelMapper.map(matiere, MatiereDto.class)
                ), HttpStatus.OK);
    }

    @GetMapping("without-data/{id}")
    public ResponseEntity<MatiereDto> getByIdWithoutData(@PathVariable String id){
        modelMapper.typeMap(Matiere.class, MatiereDto.class)
                .addMappings(m -> m.skip(MatiereDto::setClasses));

        return new ResponseEntity<>(
                modelMapper.map(this.matiereServiceImp.getByIdWithoutData(id), MatiereDto.class)
                , HttpStatus.OK
        );
    }

}
