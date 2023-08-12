import {AfterViewInit, ChangeDetectorRef, Component, Input, OnInit} from '@angular/core';
import {Tirage} from "../../../../@core/models/tirage/tirage";
import {ResponseMessage} from "../../../../@core/utils/models/response-message";
import {TirageService} from "../../../../@core/service/tirage-service/tirage.service";
import {MessageService} from "primeng/api";
import {Router} from "@angular/router";

@Component({
  selector: 'app-list-tirage',
  templateUrl: './list-tirage.component.html',
  styleUrls: ['./list-tirage.component.scss']
})
export class ListTirageComponent implements OnInit, AfterViewInit {

  listtTirage : Tirage[] = []
  @Input() matiereId: string | undefined ;
  totalElements: number = 0;
  pages = 0;
  rows = 5;
  responseMessage: ResponseMessage = new ResponseMessage([]);
  loading: boolean = false;
  //@Input() index: number | undefined;
  constructor(private tirageService: TirageService, private router: Router, private cdRef: ChangeDetectorRef, private messageService: MessageService) { }

  ngOnInit(): void {
  }
  ngAfterViewInit() {
    this.getAllTirage(this.pages, this.rows)
  }
  getAllTirage(page: number, size: number) {
    this.loading = true;

    this.responseMessage = new ResponseMessage([])
    this.tirageService.getAll(page, size).subscribe(value => {
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
  paginate($event: any) {
    this.pages = $event.page;
    this.rows = $event.rows;
    console.log( $event.page,  $event.rows)
   /* if (this.matiereId == undefined) {
      this.getClasses($event.page, $event.rows);
    }
    else{

      this.getClassByMatiere(this.matiereId, $event.page, $event.rows)
    }*/
    this.getAllTirage(this.pages, this.rows)

  }

  next (value: any){
    this.listtTirage = value.content;
    this.totalElements = value.totalElements;
  }
  error (er: ResponseMessage){
    this.responseMessage = er;
    this.responseMessage.error(this.responseMessage, this.messageService)
    this.loading = false;
  }
  complete() {
    this.loading = false;
  }
  details(id: string) {
    this.router.navigate(["/main/tirage/detail/"+id]);

  }
  navigate() {
    this.router.navigate(["/main/tirage/nouveau"])
  }

  checkIfEnseignant(){
    return localStorage.getItem("role") == "ENSEIGNANT";

  }
  delete(id) {
    this.responseMessage = new ResponseMessage([])
    this.tirageService.deleteTirage(id).subscribe(value => {
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
        this.getAllTirage(0, 5)
      });


  }
  checkAgent(){
    return localStorage.getItem("role") == "AGENT"
  }
}
