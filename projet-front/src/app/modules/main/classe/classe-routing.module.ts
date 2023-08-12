import {Component, NgModule} from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AddClasseComponent} from "./add-classe/add-classe.component";
import {ClasseComponent} from "./classe.component";
import {ListeClasseComponent} from "./liste-classe/liste-classe.component";
import {DetailClasseComponent} from "./detail-classe/detail-classe.component";

const routes: Routes = [
  {
    path: '',
    component: ClasseComponent,
    children: [
      {
        path: 'nouveau',
        component: AddClasseComponent
      },
      {
        path: '',
        component: ListeClasseComponent
      },
      {
        path: 'detail/:id',
        component: DetailClasseComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ClasseRoutingModule { }
