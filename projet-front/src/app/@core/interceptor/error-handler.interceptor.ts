import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor, HttpErrorResponse
} from '@angular/common/http';
import {catchError, Observable, throwError} from 'rxjs';
import {ResponseMessage} from "../utils/models/response-message";

@Injectable()
export class ErrorHandlerInterceptor implements HttpInterceptor {

  constructor() {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    return next.handle(request)
      .pipe(
        catchError((er: HttpErrorResponse) => {
          return throwError(this.errorHandler(er))
        })
      )

  }
  errorHandler(er: HttpErrorResponse): ResponseMessage {
    let response: ResponseMessage = new ResponseMessage([]);
    response.status = er.status;
    switch (er.status) {
      case 422:
        response.message = er.error.message;
        response.validations = er.error.validations;
        break;
      case 400:
        console.log(er);
        response.message = er.error.message;
        response.validations = er.error.validations;
        break
      case 500:
        response.message = "Quelque chose s'est mal passé.";
        break;
      case 503:
        response.message = "Service non disponible.";
        break;
      case 0:
        response.message = "Connexion echoué avec le serveur.";
        break;
      default:
        response.message = er.error.message;
    }
    return response;
  }
}
