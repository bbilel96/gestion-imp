package tn.iit.service.micro.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.iit.service.micro.app.model.Classe;
import tn.iit.service.micro.app.model.Matiere;

import java.util.List;

@Repository
public interface ClasseRepository extends JpaRepository<Classe, String> {
    Page<Classe> findAllByMatieres_id(Pageable pageable, String id);
    List<Classe> findAllByMatieres_id(String id);
}
