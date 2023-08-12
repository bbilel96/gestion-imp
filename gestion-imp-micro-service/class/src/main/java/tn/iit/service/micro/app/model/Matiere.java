package tn.iit.service.micro.app.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import tn.iit.service.micro.dto.personne.AgentDto;
import tn.iit.service.micro.dto.personne.EnseignantDto;
import tn.iit.service.micro.keygen.StringPrefixSequenceGenerator;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "t_matiere")
public class Matiere implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "matiere_sequence")
    @GenericGenerator(name = "matiere_sequence",
            strategy = "tn.iit.service.micro.keygen.StringPrefixSequenceGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = StringPrefixSequenceGenerator.INCREMENT_PARAM, value = "50"),
                    @org.hibernate.annotations.Parameter(name = StringPrefixSequenceGenerator.VALUE_PREFIX_PARAMETER, value = "M_"),
                    @org.hibernate.annotations.Parameter(name = StringPrefixSequenceGenerator.NUMBER_FORMAT_DEFAULT, value = "%05d"),


            })
    @EqualsAndHashCode.Include
    private String id;
    @Column(length = 50, nullable = false)
    private String nom;
    private String enseignantId;
    @ToString.Exclude
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "classe_matiere",
            joinColumns = @JoinColumn(name = "matiere_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "classe_id", referencedColumnName = "id"))

    private Set<Classe> classes = new HashSet<>();
    @Transient
    private EnseignantDto enseignantDto;

    public void update(Matiere matiere) {
        this.nom = matiere.getNom();
        this.enseignantId = matiere.getEnseignantId();
    }


}
