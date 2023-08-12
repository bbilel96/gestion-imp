import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {User} from "../../../models/user/user";
import {Observable} from "rxjs";
import {ResponseMessage} from "../../../utils/models/response-message";
import {environment} from "../../../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class TirageAgentService {

  constructor(private httpClient: HttpClient) { }

  public createAgentTirage(agentTirage: User): Observable<ResponseMessage>{
    return this.httpClient.post<ResponseMessage>(environment.url + "personne/agent", agentTirage);
  }
}
