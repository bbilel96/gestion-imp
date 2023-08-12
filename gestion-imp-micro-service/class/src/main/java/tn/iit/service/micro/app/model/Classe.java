package tn.iit.service.micro.app.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
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
@Table(name = "t_classe")
public class Classe implements Serializable {
    @Serial
    private static final long serialVersionUID = 2L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "matiere_sequence")
    @SequenceGenerator(name = "matiere_sequence")
    @Column(nullable = false)
    @GenericGenerator(name = "matiere_sequence",
            strategy = "tn.iit.service.micro.keygen.StringPrefixSequenceGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = StringPrefixSequenceGenerator.INCREMENT_PARAM, value = "50"),
                    @org.hibernate.annotations.Parameter(name = StringPrefixSequenceGenerator.VALUE_PREFIX_PARAMETER, value = "C_"),
                    @org.hibernate.annotations.Parameter(name = StringPrefixSequenceGenerator.NUMBER_FORMAT_DEFAULT, value = "%05d"),


            })
    @EqualsAndHashCode.Include
    private String id;
    @ToString.Include
    @Column(length = 50, nullable = false)
    private String nom;
    @Column(nullable = false)
    private int nbEtudiant;
    @ToString.Exclude
    @ManyToMany
    @JoinTable(
            name = "classe_matiere",
            joinColumns = @JoinColumn(name = "classe_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "matiere_id", referencedColumnName = "id"))
    private Set<Matiere> matieres = new HashSet<>();

    public void update(Classe classe) {
        this.nom = classe.getNom();
        this.nbEtudiant = classe.getNbEtudiant();
    }
}
