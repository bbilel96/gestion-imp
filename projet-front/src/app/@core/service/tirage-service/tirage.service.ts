import {Injectable} from '@angular/core';
import {HttpClient, HttpEvent} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../../../environments/environment";
import {ResponseMessage} from "../../utils/models/response-message";
import {Tirage} from "../../models/tirage/tirage";

@Injectable({
  providedIn: 'root'
})
export class TirageService {

  constructor(private httpClient: HttpClient) {
  }


  public getTirageByAgent(id: string | undefined, page: number, size: number): Observable<any> {
    return this.httpClient.get<any>(environment.url + "tirage/agent/" + id + "/all/page/" + page + "/size/" + size);
  }

  public getAll(page: number, size: number): Observable<any> {
   let header = new Headers({
      Authorization: `Bearer ${localStorage.getItem("token")}`
    });
    return this.httpClient.get<any>(environment.url + "tirage/all/page/" + page + "/size/" + size);
  }

  public addTirage(enseignantId: string | undefined, classeId: string | undefined, formData: FormData): Observable<HttpEvent<ResponseMessage>> {
    return this.httpClient.post<ResponseMessage>(environment.url + "tirage/" + enseignantId + "/" + classeId, formData, {
      reportProgress: true,
      observe: 'events'
    });
  }
  public updateTirage(tirageId: string, formData: FormData): Observable<HttpEvent<ResponseMessage>> {
    return this.httpClient.put<ResponseMessage>(environment.url + "tirage/" + tirageId , formData, {
      reportProgress: true,
      observe: 'events'
    });
  }

  public getTirageWithAllData(tirageId: string | undefined): Observable<Tirage> {
    return this.httpClient.get<Tirage>(environment.url + "tirage/all-data/" + tirageId);
  }
  public download (tirageId: string | undefined, filename: string | undefined): Observable<HttpEvent<Blob>>{
    return this.httpClient.get(environment.url + "tirage/download/" + filename + "/" + tirageId, {
      reportProgress: true,
      observe: 'events',
      responseType: 'blob'
    });
  }
  public changeStatus (tirageId: string, agentId: string, status: string): Observable<ResponseMessage>{
    return this.httpClient.get<ResponseMessage>(environment.url + "tirage/" + tirageId + "/agent/" + agentId + "/status/" + status + "/");
  }
  public deleteTirage(tirageId: string): Observable<ResponseMessage>{
    return this.httpClient.delete<ResponseMessage>(environment.url + "tirage/" + tirageId);

  }
}
