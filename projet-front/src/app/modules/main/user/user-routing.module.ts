import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {UserComponent} from "./user.component";
import {AddUserComponent} from "./add-user/add-user.component";
import {ListUserComponent} from "./list-user/list-user.component";
import {DetailUserComponent} from "./detail-user/detail-user.component";

const routes: Routes = [
  {
    path:'',
    component: UserComponent,
    children: [
      {
        path: 'nouveau',
        component: AddUserComponent
      },
      {
        path:"",
        component: ListUserComponent
      },
      {
        path: 'detail/:id',
        component: DetailUserComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule { }
