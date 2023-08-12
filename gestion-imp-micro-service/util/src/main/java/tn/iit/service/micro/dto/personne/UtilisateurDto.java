package tn.iit.service.micro.dto.personne;

import lombok.*;
import tn.iit.service.micro.utilenum.TypeUser;
import tn.iit.service.micro.utilenum.UtilisateurStatus;

import java.time.LocalDate;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class UtilisateurDto{


    @EqualsAndHashCode.Include
    private String id;
    private String cin;
    private String dateNaissance;
    private String nom;
    private String prenom;
    private String email;
    private String numTel;
    private UtilisateurStatus status;
    private TypeUser typeUser;
}
