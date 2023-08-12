import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { MatiereRoutingModule } from './matiere-routing.module';
import { MatiereComponent } from './matiere.component';
import { AddMatiereComponent } from './add-matiere/add-matiere.component';
import {ReactiveFormsModule} from "@angular/forms";
import {PrimeNgModule} from "../../../@core/shared/module/prime-ng/prime-ng.module";
import { DetailMatiereComponent } from './detail-matiere/detail-matiere.component';
import { AffectClasseComponent } from './affect-classe/affect-classe.component';
import {DetailsModule} from "../../../@core/shared/module/details/details.module";



@NgModule({
  declarations: [
    MatiereComponent,
    AddMatiereComponent,
    DetailMatiereComponent,
    AffectClasseComponent,
  ],
  imports: [
    CommonModule,
    MatiereRoutingModule,
    ReactiveFormsModule,
    PrimeNgModule,
    DetailsModule

  ]
})
export class MatiereModule { }
