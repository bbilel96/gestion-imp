import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Matiere} from "../../models/matiere/matiere";
import {Observable} from "rxjs";
import {ResponseMessage} from "../../utils/models/response-message";
import {environment} from "../../../../environments/environment";
import {Classe} from "../../models/classe/classe";

@Injectable({
  providedIn: 'root'
})
export class MatiereService {

  constructor(private httpClient: HttpClient) {
  }

  public addMatiere(matiere: Matiere): Observable<ResponseMessage> {
    return this.httpClient.post<ResponseMessage>(environment.url + "classe/matiere", matiere)
  }

  public getAll(page: number, size: number): Observable<any> {
    return this.httpClient.get<any>(environment.url + "classe/matiere/page/" + page + "/size/" + size);
  }

  public getMatiereByClasse(classeId: string | undefined, page: number, size: number): Observable<any> {
    return this.httpClient.get<any>(environment.url + "classe/matiere/classe/" + classeId + "/page/" + page + "/size/" + size);
  }
  public getAllWithoutPage(): Observable<Matiere[]>{
    return this.httpClient.get<Matiere[]>(environment.url + "classe/matiere");
  }
  public getMatiereByClasseWithoutPage(classeId: string | undefined): Observable<Matiere[]> {
    return this.httpClient.get<Matiere[]>(environment.url + "classe/matiere/classe/" + classeId );
  }
  public getById(id: string | undefined): Observable<Matiere>{
    return this.httpClient.get<Matiere> (environment.url + "classe/matiere/" + id);
  }
  public updateMatiere (id: string | undefined, matiere: Matiere): Observable<ResponseMessage>{
    return this.httpClient.put<ResponseMessage>(environment.url + "classe/matiere/" + id, matiere);
  }
  public affectClasse(matiereId: string | undefined,classes: Matiere[]): Observable<ResponseMessage>
  {
    return this.httpClient.post<ResponseMessage>(environment.url + "classe/matiere/affect_classe/" + matiereId, classes);
  }
  public getMatiereByEnseignantId(enseignantId: string | undefined, page: number, size: number): Observable<Matiere>{
    return this.httpClient.get<Matiere> (environment.url + "classe/matiere/enseignant/" + enseignantId + "/page/" + page + "/size/" + size);
  }
  public getMAtiereByAgentId(agentId: string | undefined, page: number, size: number): Observable<Matiere>{
    return this.httpClient.get<Matiere> (environment.url + "classe/matiere/agent/" + agentId + "/page/" + page + "/size/" + size);
  }
  public getAllMatiereByEnseignantId(enseignantId: String | undefined): Observable<Matiere[]>{
    return this.httpClient.get<Matiere[]> (environment.url + "classe/matiere/enseignant/" + enseignantId);
  }
  public deleteMatiere (id: string): Observable<ResponseMessage>
  {
    return this.httpClient.delete<ResponseMessage>(environment.url+ "classe/matiere/" + id);
  }
}
