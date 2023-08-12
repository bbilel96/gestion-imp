package tn.iit.service.micro.app.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import tn.iit.service.micro.dto.personne.EnseignantDto;
import tn.iit.service.micro.dto.personne.UtilisateurDto;
import tn.iit.service.micro.utilenum.UtilisateurStatus;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;


@NoArgsConstructor
@Getter
@Setter
@Entity
@DiscriminatorValue("Enseignant")
@ToString(callSuper = true)
public class Enseignant extends Utilisateur implements Serializable {
    @Serial
    private static final long serialVersionUID = 4L;



}
