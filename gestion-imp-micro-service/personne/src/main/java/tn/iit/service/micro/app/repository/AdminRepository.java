package tn.iit.service.micro.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.iit.service.micro.app.model.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {

}
