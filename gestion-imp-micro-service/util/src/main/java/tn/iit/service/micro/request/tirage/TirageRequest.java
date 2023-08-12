package tn.iit.service.micro.request.tirage;

import lombok.*;
import tn.iit.service.micro.request.action.Create;
import tn.iit.service.micro.request.action.Update;
import tn.iit.service.micro.utilenum.TirageStatus;

import javax.persistence.Column;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class TirageRequest {
    @EqualsAndHashCode.Include
    private String id;
    //@NotNull(message = "Document pdf ne doit pas être vide.", groups = {Create.class, Update.class})
    private String documentPdf;
    @NotNull(message = "Nombre de tirage ne doit pas être vide.", groups = {Create.class, Update.class})
    @Min(value = 1, message = "Le nombre d'copies doit être compris entre 1 et 40 copies.", groups = {Create.class, Update.class})
    @Max(value = 40, message = "Le nombre d'copies doit être compris entre 1 et 40 copies.", groups = {Create.class, Update.class})
    private int nbTirage;
    @NotNull(message = "Enseignant doit être affecter.", groups = {Create.class})
    private String enseignantId;
    private String agentId;
    @NotNull(message = "Matière doit être affecter.", groups = {Create.class, Update.class})
    private String matiereId;
    @NotNull(message = "Classe doit être affecter.", groups = {Create.class, Update.class})
    private String classeId;
    @NotNull(message = "Date impression est obligatoire.", groups = {Create.class, Update.class})
    private String dateImpression;
}
