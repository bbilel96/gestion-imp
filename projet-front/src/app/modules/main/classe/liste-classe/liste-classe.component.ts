import {AfterViewInit, ChangeDetectorRef, Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {Classe} from "../../../../@core/models/classe/classe";
import {ClasseService} from "../../../../@core/service/classe-service/classe.service";
import {ResponseMessage} from "../../../../@core/utils/models/response-message";
import {MessageService} from "primeng/api";
import {Router} from "@angular/router";

@Component({
  selector: 'app-liste-classe',
  templateUrl: './liste-classe.component.html',
  styleUrls: ['./liste-classe.component.scss']
})
export class ListeClasseComponent implements OnInit, AfterViewInit, OnChanges {

  listClasse: Classe[] = []
  @Input() matiereId: string | undefined ;
  totalElements: number = 0;
  pages = 0;
  rows = 5;
  loading: boolean = false;
  @Input() index: number | undefined;

  responseMessage: ResponseMessage = new ResponseMessage([]);

  constructor(private classeService: ClasseService,
              private messageService: MessageService,
              private cdRef: ChangeDetectorRef,
              private router: Router
  ) {
  }

  ngOnInit(): void {
  }
  ngOnChanges(changes: SimpleChanges) {
    if (changes['index'].currentValue == 0) {

      if (changes['matiereId'] != undefined) {

        this.getClassByMatiere(changes['matiereId'].currentValue, this.pages, this.rows)
      }

    }
  }

  ngAfterViewInit() {

    if (this.matiereId == undefined){
      this.getClasses(0, 5)    }
    else{
      console.log(this.matiereId)
      this.getClassByMatiere(this.matiereId, this.pages, this.rows)
    }

  }

  getClasses(page: number, size: number) {
    this.loading = true;

    this.responseMessage = new ResponseMessage([])
    this.classeService.getAllClasse(page, size).subscribe(value => {
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
  getClassByMatiere(matiereId: string | undefined , page: number, size: number) {
    this.loading = true;
    console.log("aaaa" + this.matiereId)
    this.responseMessage = new ResponseMessage([])
    this.classeService.getClasseeByMatiere(matiereId, page, size).subscribe(value => {
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

    if (this.matiereId == undefined) {
      this.getClasses($event.page, $event.rows);
    }
    else{
      this.getClassByMatiere(this.matiereId, $event.page, $event.rows)
    }

  }

  next (value: any){
    this.listClasse = value.content;
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
    this.router.navigate(["/main/classe/detail/"+id]);

  }

  delete(id: string) {
    this.responseMessage = new ResponseMessage([])
    console.log(id)
    this.classeService.deleteClasse(id).subscribe(value => {
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
        this.getClasses(0, 5)
      });

  }
  checkIfAdmin(){
    return localStorage.getItem("role") == "ADMIN";

  }

  navigate() {
    this.router.navigate(['main/classe/nouveau'])
  }
}
