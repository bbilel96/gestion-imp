package tn.iit.service.micro.app.service.implementation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import tn.iit.service.micro.app.model.Admin;
import tn.iit.service.micro.app.model.Utilisateur;
import tn.iit.service.micro.app.repository.AdminRepository;
import tn.iit.service.micro.app.repository.UtilisateurRepository;
import tn.iit.service.micro.app.service.AdminService;
import tn.iit.service.micro.app.service.UtilisateurService;
import tn.iit.service.micro.exception.classexception.ObjectNotFound;
import tn.iit.service.micro.utilclass.ResponseMessage;
import tn.iit.service.micro.utilenum.ResponseBehavior;
import tn.iit.service.micro.utilenum.UtilisateurStatus;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminServiceImp implements UtilisateurService, AdminService {
    private final AdminRepository adminRepository;
    private final UtilisateurRepository utilisateurRepository;
    private final UtilisateurServiceImp utilisateurService;



    @Override
    public ResponseMessage changeStatus(UtilisateurStatus utilisateurStatus, String id) {
        log.info("Starting createPersonne(utilisateurStatus: {}, id: {}).", utilisateurStatus, id);
        Utilisateur foundedUtilisateur = utilisateurService.getById(id);
        log.info("User founded : {}", foundedUtilisateur);
        foundedUtilisateur.setStatus(utilisateurStatus);
        this.utilisateurRepository.save(foundedUtilisateur);
        log.info("createPersonne(utilisateurStatus: {}, id: {}) finished.", utilisateurStatus, id);
        return new ResponseMessage("Status est changé.", ResponseBehavior.SUCCESS);
    }

    @Override
    public ResponseMessage createUtilisateur(Utilisateur utilisateur) {
        log.info("Starting createUtilisateur(utilisateur: {}).", utilisateur);


        Admin admin = (Admin) utilisateur;
        utilisateurService.checkDuplicate(admin);
        utilisateurService.checkDateNaissance(utilisateur.getDateNaissance());
        this.adminRepository.save(admin);
        log.info("createUtilisateur(utilisateur: {}) finished.", utilisateur);
        return new ResponseMessage("Admin est ajouté avec success", ResponseBehavior.SUCCESS);
    }

    @Override
    public Page<Admin> getAll(int page, int size) {
        log.info("Starting getAll(page: {}, size: {}).", page, size);
        return this.adminRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Admin getById(String id) {
        log.info("getById(id: {}) finished.", id);
        return this.adminRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Admin with id: {}. Does not exist in db.", id);
                    throw new ObjectNotFound("Aucune admin à id:" + id);
                });
    }
}
