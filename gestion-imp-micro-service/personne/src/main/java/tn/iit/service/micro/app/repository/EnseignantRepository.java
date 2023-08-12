package tn.iit.service.micro.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.iit.service.micro.app.model.Admin;
import tn.iit.service.micro.app.model.Enseignant;

@Repository
public interface EnseignantRepository extends JpaRepository<Enseignant, String> {

}
