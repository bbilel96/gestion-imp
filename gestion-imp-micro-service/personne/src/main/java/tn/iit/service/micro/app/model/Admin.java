package tn.iit.service.micro.app.model;


import lombok.*;
import tn.iit.service.micro.dto.personne.AdminDto;
import tn.iit.service.micro.utilenum.TypeUser;
import tn.iit.service.micro.utilenum.UtilisateurStatus;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;



@NoArgsConstructor
@Getter
@Setter
@Entity
@DiscriminatorValue("Admin")
@ToString(callSuper = true)
public class Admin extends Utilisateur implements Serializable{
    @Serial
    private static final long serialVersionUID = 6L ;

    public Admin(String cin, LocalDate dateNaissance, String nom, String prenom, String password, String email, String numTel, UtilisateurStatus status, TypeUser typeUser) {
        super(cin, dateNaissance, nom, prenom, password, email, numTel, status, typeUser);
    }
}
