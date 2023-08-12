import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {User} from "../../../models/user/user";
import {Observable} from "rxjs";
import {ResponseMessage} from "../../../utils/models/response-message";
import * as http from "http";
import {environment} from "../../../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class AdminService {


  constructor(private httpClient: HttpClient) { }
  public createAdmin(admin: User): Observable<ResponseMessage>{
    return this.httpClient.post<ResponseMessage>(environment.url + "personne/admin", admin);
  }
}
