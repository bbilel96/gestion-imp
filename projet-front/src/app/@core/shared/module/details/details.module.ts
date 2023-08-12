import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {ListMatiereComponent} from "../../../../modules/main/matiere/list-matiere/list-matiere.component";
import {ListeClasseComponent} from "../../../../modules/main/classe/liste-classe/liste-classe.component";
import {PrimeNgModule} from "../prime-ng/prime-ng.module";




@NgModule({
  declarations: [
    ListMatiereComponent,
    ListeClasseComponent],
  imports: [
    CommonModule,
    PrimeNgModule,

  ],
  exports: [
    ListMatiereComponent,
    ListeClasseComponent
  ]
})
export class DetailsModule { }
