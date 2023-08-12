package tn.iit.service.micro.app.service;

import org.springframework.data.domain.Page;
import tn.iit.service.micro.app.model.Classe;
import tn.iit.service.micro.app.model.Matiere;
import tn.iit.service.micro.utilclass.ResponseMessage;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ClasseService {
    Classe getById(String id);
    ResponseMessage createClasse(Classe classe);
    ResponseMessage updateClasse (Classe classe, String id);
    ResponseMessage deleteClasse (String id);
    Page<Classe> findAll(int page, int size);
    Page<Classe> findAllByMatiereId(int page, int size, String id);
    ResponseMessage affectMatiere(String id, List<Matiere> matiereIds);
    List<Classe> findAllWithoutPage();

    List<Classe> findAllByMatiereIdWithoutPage(String id);


}
