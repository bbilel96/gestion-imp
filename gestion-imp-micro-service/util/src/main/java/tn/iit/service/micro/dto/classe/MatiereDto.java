package tn.iit.service.micro.dto.classe;

import lombok.*;
import tn.iit.service.micro.dto.personne.EnseignantDto;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MatiereDto {
    @EqualsAndHashCode.Include
    private String id;
    private String nom;
    private String enseignantId;
    private Set<ClasseDto> classes = new HashSet<>();
    private EnseignantDto enseignantDto;
}
