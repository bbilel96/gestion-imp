import {ChangeDetectorRef, Component, Input, OnInit} from '@angular/core';
import {filter, Subscription} from "rxjs";
import {MenuService} from "../../../../service/menu -service/menu.service";
import {NavigationEnd, Router} from "@angular/router";
import {MainComponent} from "../../../../../modules/main/main.component";

@Component({
  selector: 'app-menu-iteam',
  templateUrl: './menu-iteam.component.html',
  styleUrls: ['./menu-iteam.component.scss']
})
export class MenuIteamComponent implements OnInit {

  @Input() item: any;

  @Input() index: number | undefined;

  @Input() root: boolean | undefined;

  @Input() parentKey: string | undefined;

  active = false;

  menuSourceSubscription: Subscription;

  menuResetSubscription: Subscription;

  key: string = "0";

  constructor(public app: MainComponent, public router: Router, private cd: ChangeDetectorRef, private menuService: MenuService) {
    this.menuSourceSubscription = this.menuService.menuSource$.subscribe(key => {
      // deactivate current active menu
      if (this.active && this.key !== key && key.indexOf(this.key) !== 0) {
        this.active = false;
      }
    });

    this.menuResetSubscription = this.menuService.resetSource$.subscribe(() => {
      this.active = false;
    });

    this.router.events.pipe(filter(event => event instanceof NavigationEnd))
      .subscribe(params => {
        if (this.item.routerLink) {
          this.updateActiveStateFromRoute();
        } else {
          this.active = false;
        }
      });
  }

  ngOnInit() {
    if (this.item.routerLink) {
      this.updateActiveStateFromRoute();
    }

    this.key = this.parentKey ? this.parentKey + '-' + this.index : String(this.index);
  }

  updateActiveStateFromRoute() {
    this.active = this.router.isActive(this.item.routerLink[0], !this.item.items);
  }

  itemClick(event: Event) {
    event.stopPropagation();
    // avoid processing disabled items
    if (this.item.disabled) {
      event.preventDefault();
      return;
    }

    // notify other items
    this.menuService.onMenuStateChange(this.key);

    // execute command
    if (this.item.command) {
      this.item.command({originalEvent: event, item: this.item});
    }

    // toggle active state
    if (this.item.items) {
      this.active = !this.active;
    } else {
      // activate item
      this.active = true;

      // hide overlay menus
      this.app.menuActiveMobile = false;

      if (this.app.isDesktop() && this.app.isOverlay()) {
        this.app.menuInactiveDesktop = true;
      }
    }
  }

  ngOnDestroy() {
    if (this.menuSourceSubscription) {
      this.menuSourceSubscription.unsubscribe();
    }

    if (this.menuResetSubscription) {
      this.menuResetSubscription.unsubscribe();
    }
  }
}
