package tn.iit.service.micro.app.proxy.personne;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tn.iit.service.micro.dto.personne.AgentDto;
import tn.iit.service.micro.dto.personne.EnseignantDto;
import tn.iit.service.micro.dto.personne.UtilisateurDto;

@FeignClient(name = "personne", path="/personne/")
public interface PersonneFeignClient {
    @GetMapping("enseignant/{id}")
    ResponseEntity<EnseignantDto> getByIdEns (@PathVariable(value = "id") String id);
    @GetMapping("agent/{id}")
    ResponseEntity<AgentDto> getByIdAgent (@PathVariable(value = "id") String id);

}
