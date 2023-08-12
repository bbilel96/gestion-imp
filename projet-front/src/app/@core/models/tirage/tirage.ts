import {TirageStatus} from "../../enumeration/tirage-status/tirage-status";
import {User} from "../user/user";
import {Matiere} from "../matiere/matiere";
import {Validators} from "@angular/forms";
import {Classe} from "../classe/classe";

export class Tirage {
  private _id: string | undefined;
  private _matiereId: string | undefined;
  private _enseignantId: string | undefined;
  private _nbTirage: number | undefined;
  private _status: TirageStatus | undefined;
  private _dateImpression: Date | string | null;
  private _enseignantDto: User | undefined = new User(undefined, undefined, null, undefined, undefined, undefined, undefined, undefined, undefined, undefined);
  private _matiereDto: Matiere | undefined = new Matiere(undefined, undefined, [], undefined, undefined);
  private _agentDto: User | undefined=  new User(undefined, undefined, null, undefined, undefined, undefined, undefined, undefined, undefined, undefined)
  private _classeDto: Classe | undefined ;
  private _classeId: string | undefined;
  private _documentPdf: string | undefined;
  constructor(dateImpression: Date | string | null, doumentPdf?: string, classeId?: string, classeDto?: Classe, enseignantId?: string, matiereId?: string, id?: string, nbTirage?: number, status?: TirageStatus,  enseignantDto?: User, matiereDto?: Matiere, agentDto?: User) {
    this._id = id;
    this._nbTirage = nbTirage;
    this._status = status;
    this._enseignantDto = enseignantDto;
    this._matiereDto = matiereDto;
    this._agentDto = agentDto;
    this._dateImpression = dateImpression;
    this._enseignantId = enseignantId;
    this._documentPdf = doumentPdf
    this._matiereId = matiereId;
    this._classeDto = classeDto
    this._classeId = classeId;
  }

  get id(): string | undefined {
    return this._id;
  }

  set id(value: string | undefined) {
    this._id = value;
  }

  get nbTirage(): number | undefined {
    return this._nbTirage;
  }

  set nbTirage(value: number | undefined) {
    this._nbTirage = value;
  }

  get status(): TirageStatus | undefined {
    return this._status;
  }

  set status(value: TirageStatus | undefined) {
    this._status = value;
  }

  get enseignantDto(): User | undefined {
    return this._enseignantDto;
  }

  set enseignantDto(value: User | undefined) {
    this._enseignantDto = value;
  }

  get matiereDto(): Matiere | undefined {
    return this._matiereDto;
  }

  set matiereDto(value: Matiere | undefined) {
    this._matiereDto = value;
  }

  get classeId(): string | undefined {
    return this._classeId;
  }

  set classeId(value: string | undefined) {
    this._classeId = value;
  }

  get agentDto(): User | undefined {
    return this._agentDto;
  }

  set agentDto(value: User | undefined) {
    this._agentDto = value;
  }

  get dateImpression(): Date | string | null {
    return this._dateImpression;
  }

  set dateImpression(value: Date | string | null) {
    this._dateImpression = value;
  }


  get classeDto(): Classe | undefined {
    return this._classeDto;
  }

  set classeDto(value: Classe | undefined) {
    this._classeDto = value;
  }

  get documentPdf(): string | undefined {
    return this._documentPdf;
  }

  set documentPdf(value: string | undefined) {
    this._documentPdf = value;
  }

  get matiereId(): string | undefined {
    return this._matiereId;
  }

  set matiereId(value: string | undefined) {
    this._matiereId = value;
  }

  get enseignantId(): string | undefined {
    return this._enseignantId;
  }

  set enseignantId(value: string | undefined) {
    this._enseignantId = value;
  }

  public static createTirageValidator(): Validators{
    return{
      'matiereDto': [""],
      'dateImpression': [""],
      'nbTirage': [""],
      'classeDto': [""]
    }
  }
  public static editTirageValidator(): Validators{
    return{
      'id': [{value: null, disabled: true}],
      'matiereDto': [""],
      'dateImpression': [""],
      'nbTirage': [""],
      'classeDto': [""],
      'status': [""]
    }
  }
}

