import {Matiere} from "../matiere/matiere";
import {Validators} from "@angular/forms";

export class Classe {
  private _id: string | undefined;
  private _nom: string | undefined;
  private _matieres: Matiere[] | undefined;
  private _nbEtudiant: number | undefined;


  constructor(id: string | undefined, nom: string | undefined, matiere: Matiere[] | undefined, nbEtudiant: number | undefined) {
    this._id = id;
    this._nom = nom;
    this._matieres = matiere;
    this._nbEtudiant = nbEtudiant;

  }


  get matieres(): Matiere[] | undefined {
    return this._matieres;
  }

  set matieres(value: Matiere[] | undefined) {
    this._matieres = value;
  }

  get nbEtudiant(): number | undefined {
    return this._nbEtudiant;
  }

  set nbEtudiant(value: number | undefined) {
    this._nbEtudiant = value;
  }

  get id(): string | undefined {
    return this._id;
  }

  set id(value: string | undefined) {
    this._id = value;
  }

  get nom(): string | undefined {
    return this._nom;
  }

  set nom(value: string | undefined) {
    this._nom = value;
  }

  get matiere(): Matiere[] | undefined {
    return this._matieres;
  }

  set matiere(value: Matiere[] | undefined) {
    this._matieres = value;
  }


  public static createClasseValidator(): Validators {
    return {
      'nom': [null],
      'nbEtudiant': [null]
    }
  }

  public static affecterMatiereValidator(): Validators {
    return {
      'matieres': [[]]
    }
  }

  public static detailClasseValidation(): Validators {
    return {
      'id': [{value: null, disabled: true}],
      'nom': [null],
      'nbEtudiant': [null]
    }
  }
}
