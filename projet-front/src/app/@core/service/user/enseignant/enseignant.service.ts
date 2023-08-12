import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {User} from "../../../models/user/user";
import {Observable} from "rxjs";
import {ResponseMessage} from "../../../utils/models/response-message";
import {environment} from "../../../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class EnseignantService {

  constructor(private httpClient: HttpClient) { }
  public createEnseignant(enseignant: User): Observable<ResponseMessage>{
    return this.httpClient.post<ResponseMessage>(environment.url + "personne/enseignant", enseignant);
  }
  public getEnseignantByMatieres(matiereId: string | undefined): Observable<User[]>{
    return this.httpClient.get<User[]>(environment.url + "personne/enseignant/matiere/" + matiereId);
  }
}
