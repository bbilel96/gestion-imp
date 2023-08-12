import {ChangeDetectorRef, Component, Input, OnInit} from '@angular/core';
import {NavigationEnd, Router} from "@angular/router";
import {filter, Subscription} from "rxjs";
import {MainComponent} from "../../../../../modules/main/main.component";

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss']
})
export class MenuComponent implements OnInit {

  model: any[] = []

  constructor(public appMain: MainComponent) {
  }

  ngOnInit(): void {
    if (localStorage.getItem("role") == "ADMIN") {


      this.model = [
        {
          label: 'Home',
          items: [
            {label: 'Profile', icon: 'pi pi-user', routerLink: ["/main/utilisateur/detail/"+ localStorage.getItem("id")]}
          ]
        },
        {
          label: 'Gestion',
          items: [
            {label: 'Utilisateur', icon: 'pi pi-users', routerLink: ['/main/utilisateur']},
            {label: 'Classe', icon: 'pi pi-clone', routerLink: ['/main/classe']},
            {label: 'Matière', icon: 'pi pi-book', routerLink: ['/main/matiere']},
            {label: 'Tirage', icon: 'pi pi-print', routerLink: ['/main/tirage']},

          ]
        }
      ];
    }else if (localStorage.getItem("role") == "ENSEIGNANT") {


      this.model = [
        {
          label: 'Home',
          items: [
            {label: 'Profile', icon: 'pi pi-user', routerLink: ['/main/utilisateur/detail/'+ localStorage.getItem("id")]}
          ]
        },
        {
          label: 'Gestion',
          items: [
            {label: 'Matière', icon: 'pi pi-book', routerLink: ['/main/matiere']},
            {label: 'Tirage', icon: 'pi pi-print', routerLink: ['/main/tirage']},

          ]
        }
      ];
    }else if (localStorage.getItem("role") == "AGENT") {


      this.model = [
        {
          label: 'Home',
          items: [
            {label: 'Profile', icon: 'pi pi-user', routerLink: ['/main/utilisateur/detail/'+ localStorage.getItem("id")]}
          ]
        },
        {
          label: 'Gestion',
          items: [
            {label: 'Tirage', icon: 'pi pi-print', routerLink: ['/main/tirage']},

          ]
        }
      ];
    }
  }

  onKeydown(event: KeyboardEvent) {
    const nodeElement = (<HTMLDivElement>event.target);
    if (event.code === 'Enter' || event.code === 'Space') {
      nodeElement.click();
      event.preventDefault();
    }
  }


}
