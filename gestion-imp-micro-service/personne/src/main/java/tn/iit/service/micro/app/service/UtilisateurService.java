package tn.iit.service.micro.app.service;


import org.springframework.data.domain.Page;
import tn.iit.service.micro.app.model.Utilisateur;
import tn.iit.service.micro.utilclass.ResponseMessage;

public interface UtilisateurService {
    ResponseMessage createUtilisateur(Utilisateur utilisateur);

    Page<? extends Utilisateur> getAll(int page, int size);

    Utilisateur getById(String id);
}
