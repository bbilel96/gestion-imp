import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Classe} from "../../models/classe/classe";
import {Observable} from "rxjs";
import {environment} from "../../../../environments/environment";
import {ResponseMessage} from "../../utils/models/response-message";
import {Matiere} from "../../models/matiere/matiere";

@Injectable({
  providedIn: 'root'
})
export class ClasseService {


  constructor(private httpClient: HttpClient) { }
  public createClasse(classe: Classe): Observable<ResponseMessage>{
    return this.httpClient.post<ResponseMessage>(environment.url + "classe/", classe)
  }
  public getAllClasse(page: number, size: number):Observable<any>{
    return this.httpClient.get<any>(environment.url + "classe/page/"+page+"/size/"+ size);
  }
  public getClasseeByMatiere(matiereId: string | undefined, page: number, size: number): Observable<any> {
    console.log(environment.url + "classe/matiere/" + matiereId + "/page/" + page + "/size/" + size)
    return this.httpClient.get<any>(environment.url + "classe/matiere/" + matiereId + "/page/" + page + "/size/" + size);
  }
  public getById(id: String | undefined): Observable<Classe>{
    return this.httpClient.get<Classe>(environment.url + "classe/" + id);
  }
  public updateClasse(classe: Classe, id: string| undefined): Observable<ResponseMessage>{
    return this.httpClient.put<ResponseMessage>(environment.url+ "classe/"+ id, classe);
  }
  public affectMatiere(classeId: string | undefined,matiers: Matiere[]): Observable<ResponseMessage>
  {
    return this.httpClient.post<ResponseMessage>(environment.url + "classe/affect_matiere/" + classeId, matiers);
  }
  public getAllWithoutPage(): Observable<Classe[]>{

    return this.httpClient.get<Classe[]>(environment.url + "classe/");
  }
  public getClasseeByMatiereWithoutPage(matiereId: string | undefined): Observable<Classe[]>{
    return this.httpClient.get<Classe[]>(environment.url + "classe/matiere/"+matiereId + "/list");
  }
  public deleteClasse (id: string): Observable<ResponseMessage>
  {
    return this.httpClient.delete<ResponseMessage>(environment.url+ "classe/" + id);
  }
}
