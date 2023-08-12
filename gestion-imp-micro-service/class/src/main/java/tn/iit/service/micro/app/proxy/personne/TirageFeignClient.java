package tn.iit.service.micro.app.proxy.personne;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tn.iit.service.micro.dto.tirage.TirageDto;
import tn.iit.service.micro.utilclass.ResponseMessage;

import javax.ws.rs.Path;
import java.util.List;

@FeignClient(name = "tirage", path="/tirage/")
public interface TirageFeignClient {
    @GetMapping("/agent/{agentId}/page/{page}/size/{size}")
    ResponseEntity<List<TirageDto>> getAllByAgentId(@PathVariable(value = "agentId") String agentId, @PathVariable(value = "page") int page, @PathVariable(value = "size") int size);
    @DeleteMapping("classe/{id}")
    ResponseEntity<ResponseMessage> deleteTirageByClasse(@PathVariable(value = "id") String id);
    @DeleteMapping("matiere/{id}")
    ResponseEntity<ResponseMessage> deleteTirageByMatiere(@PathVariable(value = "id") String id);
}
