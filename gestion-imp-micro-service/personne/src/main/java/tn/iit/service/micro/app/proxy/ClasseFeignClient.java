package tn.iit.service.micro.app.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tn.iit.service.micro.dto.classe.MatiereDto;

@FeignClient(name = "personne", path="/classe/")
public interface ClasseFeignClient {
    @GetMapping("matiere/{id}")
    ResponseEntity<MatiereDto> findById (@PathVariable(value = "id") String id);
}
