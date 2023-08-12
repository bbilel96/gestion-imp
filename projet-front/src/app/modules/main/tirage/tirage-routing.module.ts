import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {TirageComponent} from "./tirage.component";
import {ListTirageComponent} from "./list-tirage/list-tirage.component";
import {AddTirageComponent} from "./add-tirage/add-tirage.component";
import {DetailTirageComponent} from "./detail-tirage/detail-tirage.component";

const routes: Routes = [
  {
    path: '',
    component: TirageComponent,
    children: [
      {
        path: '',
        component: ListTirageComponent
      },
      {
        path: 'nouveau',
        component: AddTirageComponent
      },
      {
        path: 'detail/:id',
        component: DetailTirageComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TirageRoutingModule { }
