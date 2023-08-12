import {Classe} from "../classe/classe";
import {Validators} from "@angular/forms";
import {User} from "../user/user";

export class Matiere {
  private _id: string | undefined;
  private _nom: string | undefined;
  private _classes: Classe[] = [];
  private _enseignantDto: User | undefined;
  private _enseignantId: String | undefined;


  constructor(id: string | undefined, nom: string | undefined, classes: Classe[], enseignantDto: User | undefined, enseignantID: String | undefined) {
    this._id = id;
    this._nom = nom;
    this._classes = classes;
    this._enseignantDto = enseignantDto;
    this._enseignantId = enseignantID;
  }


  get enseignantId(): String | undefined {
    return this._enseignantId;
  }

  set enseignantId(value: String | undefined) {
    this._enseignantId = value;
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

  get enseignantDto(): User | undefined {
    return this._enseignantDto;
  }

  set enseignantDto(value: User | undefined) {
    this._enseignantDto = value;
  }

  set nom(value: string | undefined) {
    this._nom = value;
  }

  get classes(): Classe[] {
    return this._classes;
  }

  set classes(value: Classe[]) {
    this._classes = value;
  }

  public static createMatiereValidator(): Validators{
    return{
      'nom': [""]
    }
  }
  public static editMatiereValidator(): Validators{
    return{
      'id': [{value: null, disabled: true}],
      'nom': [null],
      'enseignant': [[]]
    }
  }
  public static affectClasseValidator(): Validators{
    return{
      'classes': [[]]
    }
  }
}
