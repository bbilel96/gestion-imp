import {Validators} from "@angular/forms";

export class User {
  private _id: string | undefined;
  private _cin: string | undefined;
  private _dateNaissance: Date | string | null;
  private _nom: string | undefined;
  private _prenom: string | undefined;
  private _password: string | undefined;
  private _email: string | undefined;
  private _numTel: string | undefined;
  private _status: string | undefined
  private _typeUser: string | undefined;


    constructor(id: string | undefined, cin: string | undefined, dateNaissance: Date | string | null, nom: string | undefined, prenom: string | undefined, password: string | undefined, email: string | undefined, numTel: string | undefined, status: string | undefined, typeUser: string | undefined) {
    this._id = id;
    this._cin = cin;
    this._dateNaissance = dateNaissance;
    this._nom = nom;
    this._prenom = prenom;
    this._password = password;
    this._email = email;
    this._numTel = numTel;
    this._typeUser = typeUser;
    this._status = status;
  }

  get id(): string | undefined {
    return this._id;
  }

  set id(value: string | undefined) {
    this._id = value;
  }

  get cin(): string | undefined {
    return this._cin;
  }

  set cin(value: string | undefined) {
    this._cin = value;
  }

  get dateNaissance(): Date | string | null {
    return this._dateNaissance;
  }

  set dateNaissance(value: Date | string | null) {
    this._dateNaissance = value;
  }

  get typeUser(): string | undefined {
    return this._typeUser;
  }

  set typeUser(value: string | undefined) {
    this._typeUser = value;
  }

  get nom(): string | undefined {
    return this._nom;
  }

  set nom(value: string | undefined) {
    this._nom = value;
  }

  get prenom(): string | undefined {
    return this._prenom;
  }

  set prenom(value: string | undefined) {
    this._prenom = value;
  }

  get password(): string | undefined {
    return this._password;
  }

  set password(value: string | undefined) {
    this._password = value;
  }

  get email(): string | undefined {
    return this._email;
  }

  set email(value: string | undefined) {
    this._email = value;
  }

  get numTel(): string | undefined {
    return this._numTel;
  }

  set numTel(value: string | undefined) {
    this._numTel = value;
  }

  get status(): string | undefined {
    return this._status;
  }

  set status(value: string | undefined) {
    this._status = value;
  }

  public static addUserValidator(): Validators {
    return {
      'cin': [''],
      'dateNaissance': [''],
      'nom': [''],
      'prenom': [''],
      'password': [''],
      'email': [''],
      'numTel': [''],
      'typeUser': ['Enseignant', Validators.required]

    }
  }

  public static affectEnseignantValidator(): Validators {
    return {
      'enseignants': [[]]
    }
  }

  static editUserValidator(): Validators {
    return {
      'id': [{value: null, disabled: true}],
      'cin': [''],
      'dateNaissance': [''],
      'nom': [''],
      'prenom': [''],
      'password': [''],
      'email': [{value: null, disabled: true}],
      'numTel': ['']

    }
  }
  static loginValidator(): Validators {
    return {

      'password': [''],
      'email': [''],


    }
  }
}
