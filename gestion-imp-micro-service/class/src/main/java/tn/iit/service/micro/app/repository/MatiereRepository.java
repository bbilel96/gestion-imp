package tn.iit.service.micro.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.iit.service.micro.app.model.Matiere;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatiereRepository extends JpaRepository<Matiere, String> {
    Optional<Matiere> findMatiereByNom(String nom);
    Page<Matiere> findMatiereByClasses_id(Pageable pageable, String id);
    List<Matiere> findMatiereByClasses_id(String id);
    Page<Matiere> findMatiereByEnseignantId(Pageable pageable, String id);
    List<Matiere> findAllByEnseignantId(String id);

}
