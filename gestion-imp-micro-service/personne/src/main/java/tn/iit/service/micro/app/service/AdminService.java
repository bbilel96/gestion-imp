package tn.iit.service.micro.app.service;

import tn.iit.service.micro.utilclass.ResponseMessage;
import tn.iit.service.micro.utilenum.UtilisateurStatus;

public interface AdminService extends UtilisateurService{
    abstract ResponseMessage changeStatus(UtilisateurStatus utilisateurStatus, String id);
}
