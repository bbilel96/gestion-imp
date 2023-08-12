package tn.iit.service.micro.app.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import tn.iit.service.micro.app.model.Agent;
import tn.iit.service.micro.app.model.Utilisateur;
import tn.iit.service.micro.app.repository.AgentRepository;
import tn.iit.service.micro.app.service.AgentService;
import tn.iit.service.micro.app.service.UtilisateurService;
import tn.iit.service.micro.exception.classexception.ObjectNotFound;
import tn.iit.service.micro.utilclass.ResponseMessage;
import tn.iit.service.micro.utilenum.ResponseBehavior;

@Slf4j
@Service
@Qualifier("agentServiceImp")
@RequiredArgsConstructor
public class AgentServiceImp  implements UtilisateurService, AgentService {
    private final AgentRepository agentRepository;
    private final UtilisateurServiceImp utilisateurServiceImp;


    @Override
    public ResponseMessage createUtilisateur(Utilisateur utilisateur) {
            log.info("Starting createPersonne(personne: {}).", utilisateur);
            Agent agent = (Agent) utilisateur;
        utilisateurServiceImp.checkDuplicate(agent);
        utilisateurServiceImp.checkDateNaissance(utilisateur.getDateNaissance());
            this.agentRepository.save(agent);
            log.info("createPersonne(personne: {}) finished.", utilisateur);
            return new ResponseMessage("Agent est ajouté avec success", ResponseBehavior.SUCCESS);
        }

    @Override
    public Page<? extends Utilisateur> getAll(int page, int size) {
            log.info("Starting getAllUtilisateur(page: {}, size: {}).", page, size);
            return this.agentRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Agent getById(String id) {
        log.info("getById(id: {}) finished.", id);
         Agent agent = this.agentRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Agent with id: {}. Does not exist in db.", id);
                    throw new ObjectNotFound("Aucune Agent à id:" + id);
                });
         log.info("agent {}", agent);
         return agent;
    }
}
