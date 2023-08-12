package tn.iit.service.micro.request.personne;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import tn.iit.service.micro.request.action.Auth;
import tn.iit.service.micro.request.action.Create;
import tn.iit.service.micro.request.action.Update;
import tn.iit.service.micro.utilenum.TypeUser;
import tn.iit.service.micro.utilenum.UtilisateurStatus;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UtilisateurRequest{
    @EqualsAndHashCode.Include
    private String id;
    @Pattern(regexp = "\\d{8}", message = "Cin doit être composer de 8 chiffres.", groups = {Create.class})
    @NotEmpty(message = "Cin ne doit pas être vide.", groups = {Create.class})
    private String cin;
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Date naissance n'est pas valide.", groups = {Create.class})
    @NotNull(message = "Date naissance ne doit pas être vide.", groups = {Create.class})
    private String dateNaissance;
    @NotEmpty(message = "Nom ne doit pas être vide.", groups = {Create.class, Update.class})
    @Length(max = 20, message = "Nom  doit être composer au maximum de 20 caractères.", groups = {Create.class, Update.class})
    private String nom;
    @NotEmpty(message = "Prénom ne doit pas être vide.", groups = {Create.class, Update.class})
    @Length(max = 20, message = "Prénom doit être composer au maximum de 20 caractères.", groups = {Create.class, Update.class})
    private String prenom;
    @NotEmpty(message = "Mot de passe ne doit pas être vide.", groups = {Create.class, Update.class, Auth.class})
    private String password;

    @Email(message = "Email n'est pas valide.", groups = {Create.class, Auth.class})
    @Length(min = 5, max = 50, message = "Email doit être compris entre 5 et 50 caractères.", groups = {Create.class})
    private String email;
    @Pattern(regexp = "\\d{8}", message = "Numéro telephone doit être composer de 8 chiffres.", groups = {Create.class, Update.class})
    private String numTel;
    private UtilisateurStatus status = UtilisateurStatus.ACTIVER;
    @NotNull(message = "Role d'utilisateur ne doit pas être vide.", groups = {Create.class})
    private TypeUser typeUser;


}
