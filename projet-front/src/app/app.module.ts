import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {PrimeNgModule} from "./@core/shared/module/prime-ng/prime-ng.module";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {ClasseService} from "./@core/service/classe-service/classe.service";
import {MessageService} from "primeng/api";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {ErrorHandlerInterceptor} from "./@core/interceptor/error-handler.interceptor";
import {DatePipe} from "@angular/common";
import {TokenInterceptor} from "./@core/interceptor/token.interceptor";

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    PrimeNgModule,
    HttpClientModule,
    BrowserAnimationsModule
  ],
  providers: [
    ClasseService,
    DatePipe,
    MessageService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ErrorHandlerInterceptor,
      multi: true
    },
    {
      provide : HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi   : true,
    },
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
