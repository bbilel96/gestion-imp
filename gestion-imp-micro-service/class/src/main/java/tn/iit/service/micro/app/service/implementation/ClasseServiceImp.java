package tn.iit.service.micro.app.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import tn.iit.service.micro.app.model.Classe;
import tn.iit.service.micro.app.model.Matiere;
import tn.iit.service.micro.app.proxy.personne.TirageFeignClient;
import tn.iit.service.micro.app.repository.ClasseRepository;
import tn.iit.service.micro.app.repository.MatiereRepository;
import tn.iit.service.micro.app.service.ClasseService;
import tn.iit.service.micro.exception.classexception.ObjectNotFound;
import tn.iit.service.micro.utilclass.ResponseMessage;
import tn.iit.service.micro.utilenum.ResponseBehavior;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClasseServiceImp implements ClasseService {
    private final ClasseRepository classeRepository;
    private final MatiereRepository matiereRepository;
    private final TirageFeignClient tirageFeignClient;


    @Override
    public Classe getById(String id) {
        log.info("Starting getById(id: {})", id);
        return this.classeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Classe does not exist in db.");
                    throw new ObjectNotFound("Aucune classe à une id: " + id);
                });
    }

    @Override
    public ResponseMessage createClasse(Classe classe) {
        log.info("Starting getById(classe: {}).", classe);
        this.classeRepository.save(classe);
        log.info("getById(classe: {}) finished.", classe);
        return new ResponseMessage("Classe est ajouté.", ResponseBehavior.SUCCESS);

    }

    @Override
    public ResponseMessage updateClasse(Classe classe, String id) {
        log.info("Starting updateClasse(classe: {}, id: {}).", classe, id);
        Classe foundedClasse = this.classeRepository.findById(id).orElseThrow(() ->
        {
            log.error("Classe does not exist in db.");
            throw new ObjectNotFound("Classe n'existe pas.");
        });
        foundedClasse.update(classe);
        this.classeRepository.save(foundedClasse);
        log.info("updateClasse(classe: {}, id: {}) finished.", classe, id);
        return new ResponseMessage("Classe est modifié.", ResponseBehavior.SUCCESS);
    }

    @Override
    public ResponseMessage deleteClasse(String id) {
        log.info("Starting deleteClasse(id: {}).", id);
        Classe foundedClasse = this.classeRepository.findById(id).orElseThrow(() ->
        {
            log.error("Classe does not exist in db.");
            throw new ObjectNotFound("Classe n'existe pas.");
        });
        this.tirageFeignClient.deleteTirageByClasse(id);
        this.classeRepository.delete(foundedClasse);
        log.info("deleteClasse(id: {}) finished.", id);
        return new ResponseMessage("Classe est supprimer.", ResponseBehavior.SUCCESS);
    }

    @Override
    public Page<Classe> findAll(int page, int size) {
        log.info("Starting deleteClasse(page: {}, size: {}).", page, size);
        return this.classeRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Page<Classe> findAllByMatiereId(int page, int size, String id) {
        log.info("Starting findAllByMatiereId(page: {}, size: {}, id: {}).", page, size, id);
        return this.classeRepository.findAllByMatieres_id(PageRequest.of(page, size), id);
    }

    @Override
    public ResponseMessage affectMatiere(String id, List<Matiere> matiereIds) {
        log.info("Starting affectMatiere(id: {}, matiereIds: {}).", id, matiereIds);
        Classe foundedClasse = this.getById(id);
        Set<Matiere> affectedMatiere = new HashSet<>();
        matiereIds.forEach(matiereId -> {
            Matiere matiere = this.findMatiereById(matiereId.getId());
            affectedMatiere.add(matiere);
        });
        foundedClasse.setMatieres(affectedMatiere);
        classeRepository.save(foundedClasse);

        log.info("affectMatiere(id: {}, matiereId: {}) finished.", id, matiereIds);
        return new ResponseMessage("Matiere est affect au classe.", ResponseBehavior.SUCCESS);

    }

    @Override
    public List<Classe> findAllWithoutPage() {
        log.info("Starting findAllWithoutPage().");
        return this.classeRepository.findAll();
    }

    @Override
    public List<Classe> findAllByMatiereIdWithoutPage(String id) {
        log.info("Starting findAllByMatiereId(id: {}).", id);
        return this.classeRepository.findAllByMatieres_id(id);
    }
    public Matiere findMatiereById(String id){
        log.info("Starting findMatiereById(id: {}).", id);
        return this.matiereRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Matière does not exist in db.");
                    throw new ObjectNotFound("Matière n'existe pas.");
                });
    }
}
