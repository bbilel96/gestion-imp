package tn.iit.service.micro.app.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;


@NoArgsConstructor
@Getter
@Setter
@Entity
@DiscriminatorValue("AgentTirage")
@ToString(callSuper = true)
public class Agent extends Utilisateur implements Serializable {
    @Serial
    private static final long serialVersionUID = 5L;


}
