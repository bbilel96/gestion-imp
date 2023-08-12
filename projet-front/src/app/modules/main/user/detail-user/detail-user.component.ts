import {AfterViewInit, Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-detail-user',
  templateUrl: './detail-user.component.html',
  styleUrls: ['./detail-user.component.scss']
})
export class DetailUserComponent implements OnInit{
  userId: string | undefined = "U_53";
  index : number | undefined = 0;
  constructor(private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.userId = this.activatedRoute.snapshot.paramMap.get('id');
  }
 dispalyIfAgent(): boolean{
    return localStorage.getItem("role") == "AGENT";

 }
  displayIfEnseignant(): boolean{
    return localStorage.getItem("role") == "ENSEIGNANT";

  }

  handelTabViewChange(e: any) {
    this.index  = e.index;
  }

}
