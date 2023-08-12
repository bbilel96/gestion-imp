package tn.iit.service.micro.app.proxy.personne;

import org.apache.catalina.authenticator.SavedRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tn.iit.service.micro.dto.classe.ClasseDto;
import tn.iit.service.micro.dto.classe.MatiereDto;

import java.util.List;

@FeignClient(name = "classe", path="/classe/")

public interface ClasseFeignClient {

    @GetMapping("matiere/{id}")
    ResponseEntity<MatiereDto> getById (@PathVariable(name = "id") String id);
    @GetMapping("matiere/{id}/list")
    ResponseEntity<List<ClasseDto>> findAllByMatiereIdWithoutPage (@PathVariable(name = "id") String id);
    @GetMapping("{id}")
    ResponseEntity<ClasseDto> getClasseById(@PathVariable(name = "id") String id);

    @GetMapping("matiere/without-data/{id}")
    ResponseEntity<MatiereDto> getMatiereByIdWithoutData(@PathVariable(name = "id") String id);
}
