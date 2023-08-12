package tn.iit.service.micro.app.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import tn.iit.service.micro.app.model.Utilisateur;
import tn.iit.service.micro.app.repository.UtilisateurRepository;
import tn.iit.service.micro.app.service.UtilisateurService;
import tn.iit.service.micro.exception.classexception.BadRequestException;
import tn.iit.service.micro.exception.classexception.ObjectFound;
import tn.iit.service.micro.exception.classexception.ObjectNotFound;
import tn.iit.service.micro.utilclass.ResponseMessage;
import tn.iit.service.micro.utilenum.ResponseBehavior;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class UtilisateurServiceImp implements UtilisateurService {
    private final UtilisateurRepository utilisateurRepository;


    @Override
    public ResponseMessage createUtilisateur(Utilisateur utilisateur) {
        return null;
    }

    @Override
    public Page<Utilisateur> getAll(int page, int size) {
        log.info("Starting getAll(page: {}, size: {}).", page, size);
        return this.utilisateurRepository.findAll(PageRequest.of(page, size));
    }

    public List<Utilisateur> checkDuplicate(Utilisateur utilisateur) {
        log.info("Starting addTirage(utilisateur: {}).", utilisateur);
        List<Utilisateur> utilisateurList = this.utilisateurRepository.findByEmailOrNumTelOrCin(utilisateur.getEmail(), utilisateur.getNumTel(), utilisateur.getCin());
        if (!utilisateurList.isEmpty()) {
            log.error("Personne already existe, email: {}, numTel: {}", utilisateur.getNumTel(), utilisateur.getEmail());
            throw new ObjectFound("Email, cin ou numero telephone existe deja");
        }
        return utilisateurList;

    }
    public void checkDateNaissance(LocalDate dateNaissance){
        if (LocalDate.now().isBefore(dateNaissance)){
            throw new BadRequestException("date doit être avant la date d'aujourd'hui");
        }
    }

    public ResponseMessage updateUtilisateur(Utilisateur utilisateur, String id) {
        log.info("Starting updateUtilisateur(utilisateur: {}).", utilisateur);
        Utilisateur foundedUtilisateur = this.getById(id);
         this.utilisateurRepository.findByEmailOrNumTelOrCin(utilisateur.getEmail(), utilisateur.getNumTel(), utilisateur.getCin())
                .forEach(utilisateur1 -> {
                    if (!utilisateur1.equals(foundedUtilisateur)){
                        log.error("Personne already existe, email: {}, numTel: {}", utilisateur.getNumTel(), utilisateur.getEmail());
                        throw new ObjectFound("Email, cin ou numero telephone existe deja");
                    }
                });
            foundedUtilisateur.updateUser(utilisateur);
            this.utilisateurRepository.saveAndFlush(foundedUtilisateur);
            log.info(" updatePersonne(utilisateur: {}) finished.", utilisateur);
            return new ResponseMessage("Données est modifié avec success", ResponseBehavior.SUCCESS);



    }


    @Override
    public Utilisateur getById(String id) {
        log.info("Starting getById(id: {}).", id);
        return this.utilisateurRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Personne with id: {}. Does not exist in db.", id);
                    throw new ObjectNotFound("Aucune personne à id:" + id);
                });
    }
}
