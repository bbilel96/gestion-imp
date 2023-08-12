package tn.iit.service.micro.app.controller;

import feign.Response;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.iit.service.micro.app.model.Tirage;
import tn.iit.service.micro.app.service.implementation.TirageServiceImp;
import tn.iit.service.micro.dto.classe.MatiereDto;
import tn.iit.service.micro.dto.tirage.TirageDto;
import tn.iit.service.micro.request.action.Create;
import tn.iit.service.micro.request.tirage.TirageRequest;
import tn.iit.service.micro.utilclass.ResponseMessage;
import tn.iit.service.micro.utilenum.TirageStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TirageController {
    private final TirageServiceImp tirageServiceImp;
    private final ModelMapper modelMapper;

    @PostMapping(value = "{id}/{classeId}"
    )
    public ResponseEntity<ResponseMessage> createTirage(@PathVariable String id, @PathVariable String classeId, @RequestPart("pdf") MultipartFile pdf, @RequestPart("tirage") @Validated(Create.class) TirageRequest tirageRequest) throws IOException {
        return new ResponseEntity<>(this.tirageServiceImp.addTirage(modelMapper.map(tirageRequest, Tirage.class), id, classeId, pdf), HttpStatus.CREATED);
    }

    @GetMapping("{id}/agent/{agentId}/status/{status}/")
    public ResponseEntity<ResponseMessage> changeTirageStatus(@PathVariable String id, @PathVariable TirageStatus status, @PathVariable String agentId) {
        return new ResponseEntity<>(this.tirageServiceImp.changeTirageStatus(status, id, agentId), HttpStatus.OK);
    }

    @DeleteMapping("/{id}/enseignant/{ensId}")
    public ResponseEntity<ResponseMessage> deleteTirageWithEns(@PathVariable String id, @PathVariable String ensId) {
        return new ResponseEntity<>(this.tirageServiceImp.deleteTirage(id, ensId), HttpStatus.OK);
    }

    @GetMapping("page/{page}/size/{size}")
    public ResponseEntity<Page<TirageDto>> getAllTirage(@PathVariable int page, @PathVariable int size) {
        return new ResponseEntity<>(
                this.tirageServiceImp.getAll(page, size)
                        .map((tirage) ->
                                this.modelMapper.map(tirage, TirageDto.class)), HttpStatus.OK);
    }

    @GetMapping("agent/{id}/page/{page}/size/{size}")
    public ResponseEntity<List<TirageDto>> getAllTirageByAgentId(@PathVariable String id, @PathVariable int page, @PathVariable int size) {
        return new ResponseEntity<>(this.tirageServiceImp.getAllByIdAgent(id, page, size).stream().map(
                tirage -> this.modelMapper.map(tirage, TirageDto.class)).toList() , HttpStatus.OK);
    }

    @GetMapping("enseignant/{id}/page/{page}/size/{size}")
    public ResponseEntity<Page<TirageDto>> getAllTirageByEnseignantId(@PathVariable String id, @PathVariable int page, @PathVariable int size) {
        return new ResponseEntity<>(this.tirageServiceImp.getAllByIdEnseignant(id, page, size).map(
                tirage -> this.modelMapper.map(tirage, TirageDto.class)), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<TirageDto> getTirageById(@PathVariable String id) {

        return new ResponseEntity<>(
                modelMapper.map(
                        this.tirageServiceImp.getById(id),
                        TirageDto.class
                ),
                HttpStatus.OK
        );
    }
    @PutMapping("{id}")
    public ResponseEntity<ResponseMessage> updateTirage(@PathVariable String id, @RequestPart("pdf") MultipartFile pdf, @RequestPart("tirage") @Validated(Create.class) TirageRequest tirageRequest) throws IOException {
        return new ResponseEntity<>(
                tirageServiceImp.updateTirage(pdf,
                        modelMapper.map(
                                tirageRequest,
                                Tirage.class
                        ), id
                ),
                HttpStatus.OK
        );
    }

    @GetMapping("download/{path}/{id}")
    public ResponseEntity<Resource> downloadPdf(@PathVariable String path, @PathVariable String id) throws IOException {
        Resource resource = this.tirageServiceImp.downloadPdf(path, id);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("file-name", path);
        httpHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;File-Name=" + resource.getFilename());
        return ResponseEntity
                .ok()
                .contentType(
                        MediaType.parseMediaType(
                                Files.probeContentType(resource.getFile().toPath())
                        )
                )
                .headers(httpHeaders)
                .body(resource);
    }

    @GetMapping("agent/{agentId}/all/page/{page}/size/{size}")
    public ResponseEntity<Page<TirageDto>> getAllByAgentIdWithDetails(@PathVariable String agentId, @PathVariable int page, @PathVariable int size){


        return new ResponseEntity<>(
                this.tirageServiceImp.getAllByIdAgentWithPage(agentId, page ,size)
                        .map(tirage -> this.modelMapper.map(tirage, TirageDto.class))
                , HttpStatus.OK);
    }


    @GetMapping("all/page/{page}/size/{size}")
    public ResponseEntity<Page<TirageDto>> getAllWithAllData (@PathVariable int page, @PathVariable int size){
        return new ResponseEntity<>(
                this.tirageServiceImp.getAllWithAllData(page, size)
                        .map(tirage -> this.modelMapper.map(tirage, TirageDto.class)),
                HttpStatus.OK
        );
    }
    @GetMapping("/all-data/{id}")
    public ResponseEntity<TirageDto> getByIdWithAllData (@PathVariable String id){
        return new ResponseEntity<>(modelMapper.map(this.tirageServiceImp.getByIdWithAllData(id), TirageDto.class), HttpStatus.OK);
    }
    @DeleteMapping("/classe/{id}")
    public ResponseEntity<ResponseMessage> deleteByClasse(@PathVariable String id) {
        return new ResponseEntity<>(this.tirageServiceImp.deleteTirageByClasse(id), HttpStatus.OK);
    }
    @DeleteMapping("/matiere/{id}")
    public ResponseEntity<ResponseMessage> deleteByMatiere(@PathVariable String id) {
        return new ResponseEntity<>(this.tirageServiceImp.deleteTirageByMatiere(id), HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseMessage> deleteTirage(@PathVariable String id) {
        return new ResponseEntity<>(this.tirageServiceImp.deleteTirage(id), HttpStatus.OK);
    }
}
