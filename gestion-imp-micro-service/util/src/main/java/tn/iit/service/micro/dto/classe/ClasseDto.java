package tn.iit.service.micro.dto.classe;

import lombok.*;

import java.util.HashSet;
import java.util.Set;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ClasseDto {
    @EqualsAndHashCode.Include
    private String id;
    private String nom;
    private int nbEtudiant;
    private Set<MatiereDto> matieres = new HashSet<>();


}
