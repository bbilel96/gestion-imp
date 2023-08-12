package tn.iit.service.micro.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.iit.service.micro.app.model.Utilisateur;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, String> {
    List<Utilisateur> findByEmailOrNumTelOrCin(String email, String numTel, String cin);
    Optional<Utilisateur> findByEmailAndPassword(String email, String password);
    Optional<Utilisateur> findByEmail(String email);

}
