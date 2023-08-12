package tn.iit.service.micro.app.model;


import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import tn.iit.service.micro.exception.classexception.BadRequestException;
import tn.iit.service.micro.keygen.StringPrefixSequenceGenerator;
import tn.iit.service.micro.utilenum.TypeUser;
import tn.iit.service.micro.utilenum.UtilisateurStatus;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;



@Getter()
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_utilisateur")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public  class Utilisateur implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "utilisateur_sequence")
    @GenericGenerator(name = "utilisateur_sequence",
            strategy = "tn.iit.service.micro.keygen.StringPrefixSequenceGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = StringPrefixSequenceGenerator.INCREMENT_PARAM, value = "50"),
                    @org.hibernate.annotations.Parameter(name = StringPrefixSequenceGenerator.VALUE_PREFIX_PARAMETER, value = "U_"),
                    @org.hibernate.annotations.Parameter(name = StringPrefixSequenceGenerator.NUMBER_FORMAT_DEFAULT, value = "%05d"),


            })

    @EqualsAndHashCode.Include
    private String id;
    @Column(length = 8, nullable = false, unique = true)
    private String cin;
    @Column(nullable = false)
    private LocalDate dateNaissance;
    @Column(length = 20, nullable = false)
    private String nom;
    @Column(length = 20, nullable = false)
    private String prenom;
    @Column(nullable = false)
    private String password;
    @Column(length = 50, nullable = false, unique = true)
    private String email;
    @Column(length = 10, nullable = false, unique = true)
    private String numTel;
    @Enumerated(EnumType.STRING)
    private UtilisateurStatus status = UtilisateurStatus.ACTIVER;
    @Enumerated(EnumType.STRING)
    private TypeUser typeUser;

    public Utilisateur(String cin, LocalDate dateNaissance, String nom, String prenom, String password, String email, String numTel, UtilisateurStatus status, TypeUser typeUser) {
        this.cin = cin;
        this.dateNaissance = dateNaissance;
        this.nom = nom;
        this.prenom = prenom;
        this.password = password;
        this.email = email;
        this.numTel = numTel;
        this.status = status;
        this.typeUser = typeUser;
    }

    public void updateUser(Utilisateur utilisateur) {
        this.setNom(utilisateur.getNom());
        this.setPrenom(utilisateur.getPrenom());
        this.setPassword(utilisateur.getPassword());
        this.setNumTel(utilisateur.getNumTel());
        this.setCin(utilisateur.getCin());
        this.setDateNaissance(utilisateur.getDateNaissance());
    }




}


