import {AfterViewInit, ChangeDetectorRef, Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {ResponseMessage} from "../../../../@core/utils/models/response-message";
import {Matiere} from "../../../../@core/models/matiere/matiere";
import {MatiereService} from "../../../../@core/service/matiere-service/matiere.service";
import {MessageService} from "primeng/api";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-list-matiere',
  templateUrl: './list-matiere.component.html',
  styleUrls: ['./list-matiere.component.scss']
})
export class ListMatiereComponent implements OnInit, AfterViewInit, OnChanges {
  listMatiere: Matiere[] = []
  totalElements: number = 0;
  pages = 0;
  rows = 5;
  loading: boolean = false;
  responseMessage: ResponseMessage = new ResponseMessage([]);
  @Input() classeId: string | undefined;
  @Input() index : number  | undefined;
  @Input() enseignantId: string | undefined;
  @Input() agentId: string | undefined;

  constructor(private router: Router, private matiereService: MatiereService, private messageService: MessageService, private cdRef: ChangeDetectorRef, private activatedRoute: ActivatedRoute) {

  }

  ngOnInit(): void {
  }

  ngOnChanges(changes: SimpleChanges) {

    console.log(this.enseignantId, this.classeId, changes['index'].currentValue)
    if (changes['index'].currentValue == 0) {

      if (this.classeId != undefined ) {

        this.getMatiereByClasse(this.classeId, this.pages, this.rows);

      }
      if (this.enseignantId != undefined){

        this.getMatiereByEnseignantId(changes['enseignantId'].currentValue, this.pages, this.rows)
      }


    }

  }

  ngAfterViewInit() {

    if (this.classeId == undefined && this.enseignantId == undefined) {
      if (localStorage.getItem("role") == "ENSEIGNANT"){
        this.getMatiereByEnseignantId(localStorage.getItem("id"), this.pages, this.rows);
      }
      else if (localStorage.getItem("role") == "ADMIN"){
        this.getMatieres(this.pages, this.rows);
      }

    }

  }

  checkAdmin(){
    console.log(this.enseignantId, this.classeId)
    if (localStorage.getItem("role") == "ADMIN" && this.classeId == undefined){
      return true;
    }
    return false;
  }
  getMatieres(page: number, size: number) {
    this.loading = true;

    this.responseMessage = new ResponseMessage([])
    this.matiereService.getAll(page, size).subscribe(value => {
        this.next(value)

      },
      error => {
        this.error(error)
      },
      () => {
        this.complete();
      });
    this.cdRef.detectChanges();
  }

  paginate($event: any) {
    this.pages = $event.page;
    this.rows = $event.row;
    if (this.classeId == undefined && this.enseignantId == undefined) {
      if (localStorage.getItem("role") == "ENSEIGNANT") {
        this.getMatiereByEnseignantId(localStorage.getItem("id"), $event.page, $event.rows);

      }
      else if (localStorage.getItem("role") == "ADMIN"){
        this.getMatieres($event.page, $event.rows);
      }
    } else if (this.classeId != undefined){
      this.getMatiereByClasse(this.classeId, $event.page, $event.rows)
    }
    else if (this.enseignantId != undefined){
      this.getMatiereByEnseignantId(this.enseignantId, $event.page, $event.rows)

    }
  }
  details(id: string) {
    this.router.navigate(["/main/matiere/detail/"+id]);

  }
  //
  getMatiereByClasse(classeId: string | undefined, page: number, size: number) {
    this.loading = true;

    this.responseMessage = new ResponseMessage([])
    this.matiereService.getMatiereByClasse(classeId, page, size).subscribe(value => {
        this.next(value);

      },
      error => {
        this.error(error)
      },
      () => {
        this.complete()
      });
    this.cdRef.detectChanges();
  }

  getMatiereByEnseignantId(enseignantId: string | undefined, page: number, size: number) {
    this.loading = true;

    this.responseMessage = new ResponseMessage([])
    this.matiereService.getMatiereByEnseignantId(enseignantId, page, size).subscribe(value => {
        this.next(value);

      },
      error => {
        this.error(error)
      },
      () => {
        this.complete()
      });
    this.cdRef.detectChanges();
  }


  next(value: any) {
    this.listMatiere = value.content;
    this.totalElements = value.totalElements;
  }

  error(er: ResponseMessage) {
    this.responseMessage = er;
    this.responseMessage.error(this.responseMessage, this.messageService)
    this.loading = false;
  }

  complete() {
    this.loading = false;
  }

  delete(id) {
    this.responseMessage = new ResponseMessage([])
    console.log(id)
    this.matiereService.deleteMatiere(id).subscribe(value => {
        this.responseMessage.success(value.message, '', this.messageService)

      },
      error => {
        this.responseMessage = error;
        this.responseMessage.error(error, this.messageService);
        this.loading = false;
      },
      () => {
        this.loading = false;
        this.responseMessage = new ResponseMessage([]);
        this.loading = false;
        this.getMatieres(0, 5)
      });
  }

  navigate() {
    this.router.navigate(["/main/matiere/nouveau"])
  }
  checkIfAdmin(){
    return localStorage.getItem("role") == "ADMIN";

  }
}
