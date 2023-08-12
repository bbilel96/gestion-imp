import {AfterViewInit, ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {Classe} from "../../../../@core/models/classe/classe";
import {ResponseMessage} from "../../../../@core/utils/models/response-message";
import {User} from "../../../../@core/models/user/user";
import {ClasseService} from "../../../../@core/service/classe-service/classe.service";
import {MessageService} from "primeng/api";
import {UserService} from "../../../../@core/service/user/user.service";
import {EnseignantService} from "../../../../@core/service/user/enseignant/enseignant.service";
import {TirageAgentService} from "../../../../@core/service/user/tirage/tirage-agent.service";
import {AdminService} from "../../../../@core/service/user/admin/admin.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-list-user',
  templateUrl: './list-user.component.html',
  styleUrls: ['./list-user.component.scss']
})
export class ListUserComponent implements OnInit, AfterViewInit {
  listUser: User[] = []
  totalElements: number = 0;
  pages = 0;
  rows = 5;
  loading: boolean = false;
  responseMessage: ResponseMessage = new ResponseMessage([]);
  constructor(private userService: UserService,
              private enseignantService: EnseignantService,
              private tirageAgentService: TirageAgentService,
              private adminService: AdminService,
              private messageService: MessageService,
              private cdRef: ChangeDetectorRef,
              private router: Router) { }

  ngOnInit(): void {
  }
  ngAfterViewInit() {
    this.getClasses(0, 5)
  }

  getClasses(page: number, size: number) {
    this.loading = true;

    this.responseMessage = new ResponseMessage([])
    this.userService.getAll(page, size).subscribe(value => {
        this.listUser = value.content;
        this.totalElements = value.totalElements;

      },
      error => {
        this.responseMessage = error;
        this.responseMessage.error(this.responseMessage, this.messageService)
        this.loading = false;
      },
      () => {
        this.loading = false;
      });
    this.cdRef.detectChanges();
  }

  paginate($event: any) {
    this.pages = $event.page;
    this.rows = $event.row;
    this.getClasses($event.page, $event.rows)
  }
  details(id: string) {
    this.router.navigate(["/main/utilisateur/detail/"+id]);

  }

  checkIfAdmin(){
    return localStorage.getItem("role") == "ADMIN";

  }
  navigate() {
    this.router.navigate(['main/utilisateur/nouveau'])
  }
}
