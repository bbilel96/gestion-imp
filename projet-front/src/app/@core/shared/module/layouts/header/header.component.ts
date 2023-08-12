import {AfterViewInit, Component, OnInit} from '@angular/core';
import {AppComponent} from "../../../../../app.component";
import {MainComponent} from "../../../../../modules/main/main.component";
import {Router} from "@angular/router";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  firstLetter: string | undefined
  constructor(public mainApp: MainComponent, private router: Router) {
  }

  ngOnInit(): void {
  }


  disconnect() {
    localStorage.clear()
    this.router.navigate(['/auth/login']);
  }
}
