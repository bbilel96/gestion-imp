package tn.iit.service.micro.app.service;

import tn.iit.service.micro.app.model.Enseignant;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface EnseignantService extends UtilisateurService{

    List<Enseignant> getAllWithoutPage();
}
