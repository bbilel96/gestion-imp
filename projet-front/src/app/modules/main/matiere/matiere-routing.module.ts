import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {MatiereComponent} from "./matiere.component";
import {AddMatiereComponent} from "./add-matiere/add-matiere.component";
import {ListMatiereComponent} from "./list-matiere/list-matiere.component";
import {DetailClasseComponent} from "../classe/detail-classe/detail-classe.component";
import {DetailMatiereComponent} from "./detail-matiere/detail-matiere.component";

const routes: Routes = [
  {
    path: '',
    component: MatiereComponent,
    children: [
      {
        path: 'nouveau',
        component: AddMatiereComponent
      },
      {
        path: '',
        component: ListMatiereComponent
      },
      {
        path: 'detail/:id',
        component: DetailMatiereComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MatiereRoutingModule {
}
