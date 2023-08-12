package tn.iit.service.micro.app.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import tn.iit.service.micro.app.model.Classe;
import tn.iit.service.micro.app.model.Matiere;
import tn.iit.service.micro.app.proxy.personne.PersonneFeignClient;
import tn.iit.service.micro.app.proxy.personne.TirageFeignClient;
import tn.iit.service.micro.app.repository.ClasseRepository;
import tn.iit.service.micro.app.repository.MatiereRepository;
import tn.iit.service.micro.app.service.MatiereService;
import tn.iit.service.micro.dto.classe.MatiereDto;
import tn.iit.service.micro.dto.personne.EnseignantDto;
import tn.iit.service.micro.dto.tirage.TirageDto;
import tn.iit.service.micro.exception.classexception.ObjectFound;
import tn.iit.service.micro.exception.classexception.ObjectNotFound;
import tn.iit.service.micro.utilclass.ResponseMessage;
import tn.iit.service.micro.utilenum.ResponseBehavior;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class MatiereServiceImp implements MatiereService {
    private final MatiereRepository matiereRepository;
    private final PersonneFeignClient personneFeignClient;

    private final ClasseRepository classeRepository;
    private final TirageFeignClient tirageFeignClient;

    @Override
    public ResponseMessage createMatiere(Matiere matiere) {
        log.info("Starting createMatiere(matiere: {}).", matiere);
        this.matiereRepository.findMatiereByNom(matiere.getNom())
                .ifPresent(matiere1-> {
                    log.error("Matiere name: {}, already exist in db", matiere.getNom());
                    throw new ObjectFound("Nom du matière existe deja");
                });
        matiereRepository.save(matiere);
        log.info("createMatiere(matiere: {}) finished.", matiere);
        return new ResponseMessage("Matière est ajouté", ResponseBehavior.SUCCESS);
    }

    @Override
    public ResponseMessage deleteMatiere(String id) {
        log.info("Starting deleteMatiere(id: {}).", id);
        Matiere foundedMatiere = this.matiereRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Classe does not exist in db.");
                    throw new ObjectNotFound("Classe n'existe pas.");
                });
        this.tirageFeignClient.deleteTirageByMatiere(id);
        this.matiereRepository.delete(foundedMatiere);
        log.info("deleteMatiere(id: {}) finished.", id);
        return new ResponseMessage("Matière est supprimer.", ResponseBehavior.SUCCESS);
    }

    @Override
    public ResponseMessage updateMatiere(String id, Matiere matiere) {
        log.info("Starting updateMatiere(matiere: {}, id: {}).", matiere, id);
        Matiere foundedMatiere = this.matiereRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Matière does not exist in db.");
                    throw new ObjectNotFound("Matière n'existe pas.");
                });
        foundedMatiere.update(matiere);
        this.matiereRepository.save(foundedMatiere);
        log.info("updateMatiere(matiere: {}, id: {}) finished.", matiere, id);
        return new ResponseMessage("Matière est modifié.", ResponseBehavior.SUCCESS);
    }

    @Override
    public Page<Matiere> getAll(int page, int size) {
        log.info("Starting getAll(page: {}, size: {}).", page, size);
        return this.matiereRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Page<Matiere> findAllByClasseId(String id, int page, int size) {
        return this.matiereRepository.findMatiereByClasses_id(PageRequest.of(page, size), id);
    }

    @Override
    public Matiere findById(String id) {
        log.info("Starting findById(id: {}).", id);
        Matiere matiere = this.matiereRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Matière does not exist in db.");
                    throw new ObjectNotFound("Matière n'existe pas.");
                });
        if (matiere.getEnseignantId() != null){
            log.info("{}", matiere.getEnseignantId());
            matiere.setEnseignantDto(
                    this.personneFeignClient.getByIdEns(
                            matiere.getEnseignantId()
                    ).getBody()
            );
        }
        return matiere;

    }

    @Override
    public Page<Matiere> findAllByEnseignantId(int page, int size, String id) {
        log.info("Starting findAllByEnseignantId(page: {}, size: {}, id: {})", page, size, id);
        return this.matiereRepository.findMatiereByEnseignantId(PageRequest.of(page, size), id);
    }

    @Override
    public ResponseMessage affectEnseignantToMatiere(String id, String enseignantID) {
        log.info("Starting affectEnseignantToMatiere(id: {}, enseignantID: {})", id, enseignantID);
        Matiere foundedMatiere = this.findById(id);
        EnseignantDto foundedEnseignant = this.personneFeignClient.getByIdEns(enseignantID).getBody();
        foundedMatiere.setEnseignantId(foundedEnseignant.getId());
        matiereRepository.save(foundedMatiere);
        log.info("affectEnseignantToMatiere(id: {}, enseignantID: {}) finished.", id, enseignantID);
        return new ResponseMessage("Enseignant est affecter au matiere.", ResponseBehavior.SUCCESS);
    }

    @Override
    public List<Matiere> getAllWithoutPage() {
        log.info("Starting getAllWithoutPage()");
        return this.matiereRepository.findAll();
    }

    @Override
    public List<Matiere> findAllByClasseIdWithoutPage(String id) {
        return this.matiereRepository.findMatiereByClasses_id(id);
    }

    @Override
    public ResponseMessage affectClasses(String id, List<Classe> classesId) {
        log.info("Starting affectClasses(id: {}, classesId: {}).", id, classesId);
        Matiere foundedMatiere = this.findById(id);
        Set<Classe> affectedClasses = new HashSet<>();
        classesId.forEach(matiereId -> {
            Classe matiere = this.getClasseById(matiereId.getId());
            affectedClasses.add(matiere);
        });
        foundedMatiere.setClasses(affectedClasses);
        matiereRepository.save(foundedMatiere);

        log.info("affectMatiere(id: {}, matiereId: {}) finished.", id, classesId);
        return new ResponseMessage("Classe est affect au matière.", ResponseBehavior.SUCCESS);
    }

    @Override
    public Page<Matiere> getMatiereByAgentId(String agentId, int page, int size) {
        List<Matiere> matiereList = new ArrayList<>();
        List<TirageDto> foundedTirages = this.tirageFeignClient.getAllByAgentId(agentId, page, size).getBody();
        foundedTirages.forEach(foundedTirage -> {
            matiereList.add(this.findById(foundedTirage.getMatiereId()));
        });
        return new PageImpl<>(matiereList, PageRequest.of(page, size), matiereList.size());
    }

    @Override
    public List<Matiere> getAllWithoutPageByEnseignant(String enseignantId) {
        return this.matiereRepository.findAllByEnseignantId(enseignantId);
    }

    @Override
    public Matiere getByIdWithoutData(String id) {
        return this.matiereRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFound("Matiere n'existe pas.")
                );
    }

    public Classe getClasseById(String id) {
        log.info("Starting getClasseById(id: {})", id);
        return this.classeRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Classe does not exist in db.");
                    throw new ObjectNotFound("Aucune classe à une id: " + id);
                });
    }




}
