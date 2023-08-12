import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {MainComponent} from "./main.component";

const routes: Routes = [
  {
    path: '',
    component: MainComponent,
    children: [
      {
        path: 'classe',
        loadChildren: () => import("./classe/classe.module").then(m => m.ClasseModule)
      },
      {
        path: 'matiere',
        loadChildren: () =>  import("./matiere/matiere.module").then(m => m.MatiereModule)
      },
      {
        path: 'utilisateur',
        loadChildren: () =>  import("./user/user.module").then(m => m.UserModule)
      },
      {
        path: 'tirage',
        loadChildren: () =>import('./tirage/tirage.module').then(m => m.TirageModule)
      },

    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class MainRoutingModule {
}
