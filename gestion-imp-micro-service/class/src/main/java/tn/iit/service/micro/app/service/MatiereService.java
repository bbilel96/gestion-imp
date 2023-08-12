package tn.iit.service.micro.app.service;

import feign.Response;
import org.springframework.data.domain.Page;
import tn.iit.service.micro.app.model.Classe;
import tn.iit.service.micro.app.model.Matiere;
import tn.iit.service.micro.dto.classe.MatiereDto;
import tn.iit.service.micro.dto.tirage.TirageDto;
import tn.iit.service.micro.utilclass.ResponseMessage;

import java.util.List;
import java.util.Optional;

public interface MatiereService {
    ResponseMessage createMatiere (Matiere matiere);
    ResponseMessage deleteMatiere (String id);
    ResponseMessage updateMatiere (String id, Matiere matiere);
    Page<Matiere> getAll(int page, int size);
    Page<Matiere> findAllByClasseId(String id, int page, int size);
    Matiere findById(String id);
    Page<Matiere> findAllByEnseignantId(int page, int size, String id);
    ResponseMessage affectEnseignantToMatiere(String id, String enseignantID);
    List<Matiere> getAllWithoutPage();

    List<Matiere> findAllByClasseIdWithoutPage(String id);

    ResponseMessage affectClasses(String id, List<Classe> classesId);

    Page<Matiere> getMatiereByAgentId(String agentId, int page, int size);

    List<Matiere> getAllWithoutPageByEnseignant(String id);

    Matiere getByIdWithoutData (String id);
}
