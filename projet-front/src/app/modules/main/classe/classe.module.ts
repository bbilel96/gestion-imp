import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {ClasseRoutingModule} from './classe-routing.module';
import {ClasseComponent} from './classe.component';
import {AddClasseComponent} from './add-classe/add-classe.component';
import {PrimeNgModule} from "../../../@core/shared/module/prime-ng/prime-ng.module";
import {ReactiveFormsModule} from "@angular/forms";
import {ListeClasseComponent} from './liste-classe/liste-classe.component';
import {AffectMatiereComponent} from './affect-matiere/affect-matiere.component';
import {DetailClasseComponent} from './detail-classe/detail-classe.component';
import {MatiereModule} from "../matiere/matiere.module";
import {DetailsModule} from "../../../@core/shared/module/details/details.module";


@NgModule({
  declarations: [
    ClasseComponent,
    AddClasseComponent,
    AffectMatiereComponent,
    DetailClasseComponent,

  ],
  exports: [
  ],
  imports: [
    CommonModule,
    ClasseRoutingModule,
    PrimeNgModule,
    ReactiveFormsModule,
    DetailsModule,

  ]
})
export class ClasseModule {
}
