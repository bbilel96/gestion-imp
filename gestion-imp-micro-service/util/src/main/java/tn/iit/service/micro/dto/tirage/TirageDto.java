package tn.iit.service.micro.dto.tirage;

import lombok.*;
import tn.iit.service.micro.dto.classe.ClasseDto;
import tn.iit.service.micro.dto.classe.MatiereDto;
import tn.iit.service.micro.dto.personne.AgentDto;
import tn.iit.service.micro.dto.personne.EnseignantDto;
import tn.iit.service.micro.utilenum.TirageStatus;

import javax.persistence.Column;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TirageDto {
    @EqualsAndHashCode.Include
    private String id;
    private String documentPdf;
    private int nbTirage;
    private TirageStatus status;
    private String enseignantId;
    private String agentId;
    private String matiereId;
    private String classeId;

    private EnseignantDto enseignantDto;
    private MatiereDto matiereDto;
    private AgentDto agentDto;
    private ClasseDto classeDto;
    private LocalDateTime dateImpression;

}
