package tn.iit.service.micro.app.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tn.iit.service.micro.app.model.Tirage;

import java.util.List;
import java.util.Optional;

public interface TirageRepository extends JpaRepository<Tirage, String> {
    Page<Tirage> findAllByEnseignantId(String enseignantId, Pageable pageable);
    Page<Tirage> findAllByAgentId(String agentId, Pageable pageable);

    Optional<Tirage> findByIdAndDocumentPdf(String id, String documentPdf);
    void deleteByClasseId(String classeId);
    void deleteByMatiereId(String matiereId);
    List<Tirage> getTirageByClasseIdAndMatiereId(String classeId, String matiereId);


}
