package tn.iit.service.micro.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.iit.service.micro.app.model.Agent;
import tn.iit.service.micro.app.model.Enseignant;

@Repository
public interface AgentRepository extends JpaRepository<Agent, String> {

}
