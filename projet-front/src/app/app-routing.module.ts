import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AuthGuard} from "./@core/guard/auth.guard";

const routes: Routes = [
  {
    path: 'main',
    loadChildren: () =>import("./modules/main/main.module").then(m => m.MainModule),
    canLoad: [AuthGuard]
  },
  {
    path: 'auth',
    loadChildren: () =>import('./modules/auth/auth.module').then(m => m.AuthModule)
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
