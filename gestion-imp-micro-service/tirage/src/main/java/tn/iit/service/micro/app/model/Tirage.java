package tn.iit.service.micro.app.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import tn.iit.service.micro.dto.classe.ClasseDto;
import tn.iit.service.micro.dto.classe.MatiereDto;
import tn.iit.service.micro.dto.personne.AgentDto;
import tn.iit.service.micro.dto.personne.EnseignantDto;
import tn.iit.service.micro.dto.tirage.TirageDto;
import tn.iit.service.micro.keygen.StringPrefixSequenceGenerator;
import tn.iit.service.micro.utilenum.TirageStatus;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Tirage implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tirage_sequence")
    @GenericGenerator(name = "tirage_sequence",
            strategy = "tn.iit.service.micro.keygen.StringPrefixSequenceGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = StringPrefixSequenceGenerator.INCREMENT_PARAM, value = "50"),
                    @org.hibernate.annotations.Parameter(name = StringPrefixSequenceGenerator.VALUE_PREFIX_PARAMETER, value = "T_"),
                    @org.hibernate.annotations.Parameter(name = StringPrefixSequenceGenerator.NUMBER_FORMAT_DEFAULT, value = "%05d"),
            })
    @EqualsAndHashCode.Include
    private String id;
    @Column(nullable = false)
    private String documentPdf;
    @Column(nullable = false)
    private int nbTirage = 0;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TirageStatus  status = TirageStatus.PAS_ENCORE;
    @Column(nullable = false)
    private String enseignantId;
    private String agentId;
    private String matiereId;
    @Transient
    private MatiereDto matiereDto;
    @Transient
    private AgentDto agentDto;
    @Transient
    private EnseignantDto enseignantDto;
   @Column(nullable = false)
    private LocalDateTime dateImpression;
    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime dateCreation;

    private String namedPdf;
    private String classeId;
    @Transient
    private ClasseDto classeDto;


    public Tirage(String id, String documentPdf, int nbTirage, String enseignantId, String agentId, String matiereId) {
        this.id = id;
        this.documentPdf = documentPdf;
        this.nbTirage = nbTirage;
        this.enseignantId = enseignantId;
        this.agentId = agentId;
        this.matiereId = matiereId;
    }
    public void update (Tirage tirage){
        this.nbTirage = tirage.getNbTirage();
        this.matiereId = tirage.getMatiereId();
        this.classeId = tirage.getClasseId();
        this.dateImpression = tirage.getDateImpression();
        this.documentPdf = tirage.getDocumentPdf();
        this.namedPdf = tirage.getNamedPdf();
        this.status = tirage.status;

    }
}
