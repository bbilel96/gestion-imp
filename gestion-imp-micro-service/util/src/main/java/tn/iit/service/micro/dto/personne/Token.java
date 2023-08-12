package tn.iit.service.micro.dto.personne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Token {
    private UtilisateurDto utilisateurDto;
    private String token;
}
