package tn.iit.service.micro.app.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import tn.iit.service.micro.app.model.Enseignant;
import tn.iit.service.micro.app.model.Utilisateur;
import tn.iit.service.micro.app.proxy.ClasseFeignClient;
import tn.iit.service.micro.app.repository.EnseignantRepository;
import tn.iit.service.micro.app.service.EnseignantService;
import tn.iit.service.micro.app.service.UtilisateurService;
import tn.iit.service.micro.dto.classe.MatiereDto;
import tn.iit.service.micro.exception.classexception.ObjectNotFound;
import tn.iit.service.micro.mapper.DateMapper;
import tn.iit.service.micro.utilclass.ResponseMessage;
import tn.iit.service.micro.utilenum.ResponseBehavior;
import tn.iit.service.micro.utilenum.TypeUser;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class EnseignantServiceImp  implements UtilisateurService, EnseignantService {
    private final EnseignantRepository enseignantRepository;
    private final UtilisateurServiceImp utilisateurService;
    private final ClasseFeignClient classeFeignClient;


    @Override
    public ResponseMessage createUtilisateur(Utilisateur utilisateur) {
        log.info("Starting createUtilisateur(utilisateur: {}).", utilisateur);
        Enseignant enseignant = (Enseignant) utilisateur;
        utilisateurService.checkDuplicate(enseignant);
        utilisateurService.checkDateNaissance(utilisateur.getDateNaissance());
        this.enseignantRepository.save(enseignant);
        log.info("createUtilisateur(utilisateur: {}) finished.", utilisateur);
        return new ResponseMessage("Enseignant est ajouté avec success", ResponseBehavior.SUCCESS);
    }

    @Override
    public Page<? extends Utilisateur> getAll(int page, int size) {
        log.info("Starting getAll(page: {}, size: {}).", page, size);
        return this.enseignantRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Enseignant getById(String id) {
        log.info("Starting getById(id: {}).", id);
        return this.enseignantRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Enseignant with id: {}. Does not exist in db.", id);
                    throw new ObjectNotFound("Aucune enseignant à id:" + id);
                });
    }
    @Override
    public List<Enseignant> getAllWithoutPage(){
        log.info("Starting getById().");
        return this.enseignantRepository.findAll();

    }
}
