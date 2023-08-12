package tn.iit.service.micro.request.classe;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import tn.iit.service.micro.request.action.Create;
import tn.iit.service.micro.request.action.Update;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MatiereRequest {
    private String id;
    @NotEmpty(message = "Le nom du matière ne doit pas être vide.", groups = {Create.class, Update.class})
    @Length(max = 50, message = "Le nom du matière du classe doit être composer au maximum de 50 caractères.", groups = {Create.class, Update.class})
    private String nom;
    private String enseignantId;
}
