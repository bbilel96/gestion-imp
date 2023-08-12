package tn.iit.service.micro.request.classe;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import tn.iit.service.micro.request.action.Create;
import tn.iit.service.micro.request.action.Update;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ClasseRequest {
    @EqualsAndHashCode.Include
    private String id;
    @NotEmpty(message = "Le nom du classe ne doit pas être vide.", groups = {Create.class, Update.class})
    @Length(max = 50, message = "Le nom du classe doit être composer au maximum de 50 caractères.", groups = {Create.class, Update.class})
    private String nom;
   @Min(value = 15, message = "Le nombre d'etudiant doit être compris entre 15 et 40 etudiants.", groups = {Create.class, Update.class})
   @Max(value = 40, message = "Le nombre d'etudiant doit être compris entre 15 et 40 etudiants.", groups = {Create.class, Update.class})
   private int nbEtudiant;
}
