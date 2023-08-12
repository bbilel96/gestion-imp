import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TirageRoutingModule } from './tirage-routing.module';
import { TirageComponent } from './tirage.component';
import { ListTirageComponent } from './list-tirage/list-tirage.component';
import { AddTirageComponent } from './add-tirage/add-tirage.component';
import { DetailTirageComponent } from './detail-tirage/detail-tirage.component';
import {PrimeNgModule} from "../../../@core/shared/module/prime-ng/prime-ng.module";
import {ReactiveFormsModule} from "@angular/forms";
import {CalendarModule} from "primeng/calendar";


@NgModule({
  declarations: [
    TirageComponent,
    ListTirageComponent,
    AddTirageComponent,
    DetailTirageComponent
  ],
  imports: [
    CommonModule,
    TirageRoutingModule,
    PrimeNgModule,
    ReactiveFormsModule,
    CalendarModule
  ]
})
export class TirageModule { }
