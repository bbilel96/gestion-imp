import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { UserRoutingModule } from './user-routing.module';
import { UserComponent } from './user.component';
import { AddUserComponent } from './add-user/add-user.component';
import {ReactiveFormsModule} from "@angular/forms";
import {PrimeNgModule} from "../../../@core/shared/module/prime-ng/prime-ng.module";
import {CalendarModule} from "primeng/calendar";
import { ListUserComponent } from './list-user/list-user.component';
import { DetailUserComponent } from './detail-user/detail-user.component';
import { UpdateUserComponent } from './update-user/update-user.component';
import {MatiereModule} from "../matiere/matiere.module";
import {DetailsModule} from "../../../@core/shared/module/details/details.module";


@NgModule({
  declarations: [
    UserComponent,
    AddUserComponent,
    ListUserComponent,
    DetailUserComponent,
    UpdateUserComponent
  ],
    imports: [
        CommonModule,
        UserRoutingModule,
        ReactiveFormsModule,
        PrimeNgModule,
        CalendarModule,
        MatiereModule,
        DetailsModule
    ]
})
export class UserModule { }
