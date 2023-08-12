import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from './header/header.component';
import { MenuComponent } from './menu/menu.component';
import { MenuIteamComponent } from './menu-iteam/menu-iteam.component';
import {RouterLinkActive, RouterLinkWithHref} from "@angular/router";
import {PrimeNgModule} from "../prime-ng/prime-ng.module";



@NgModule({
  declarations: [
    HeaderComponent,
    MenuComponent,
    MenuIteamComponent
  ],
  exports: [
    MenuComponent,
    HeaderComponent
  ],
  imports: [
    CommonModule,
    RouterLinkActive,
    RouterLinkWithHref,
    PrimeNgModule
  ]
})
export class LayoutsModule { }
