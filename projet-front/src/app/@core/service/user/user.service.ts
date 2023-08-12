import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {User} from "../../models/user/user";
import {Observable, tap} from "rxjs";
import {ResponseMessage} from "../../utils/models/response-message";
import {environment} from "../../../../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpClient: HttpClient) {
  }

  public getAll(page: number, size: number): Observable<any> {
    return this.httpClient.get<any>(environment.url + "personne/utilisateur/page/" + page + "/size/" + size);
  }

  public findAllWithoutPage(): Observable<User[]> {
    return this.httpClient.get<User[]>(environment.url + "personne/enseignant");
  }

  public findById(id: string | undefined): Observable<User> {
    return this.httpClient.get<User>(environment.url + "personne/utilisateur/" + id);
  }

  public updateUser(id: string | undefined, user: User): Observable<ResponseMessage> {
    return this.httpClient.put<ResponseMessage>(environment.url + "personne/utilisateur/" + id, user)
  }

  public login(user: User): Observable<any> {
    return this.httpClient.post(environment.url + "personne/auth/signIn", user)
      .pipe(tap((response: any) => {
        localStorage.setItem("token", response.token)
        localStorage.setItem("id", response.utilisateurDto.id)
        localStorage.setItem("role", response.utilisateurDto.typeUser)

        }
      ));
  }
}
