import {AfterViewInit, ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {ClasseService} from "../../../../@core/service/classe-service/classe.service";
import {ResponseMessage} from "../../../../@core/utils/models/response-message";
import {Classe} from "../../../../@core/models/classe/classe";
import {MessageService} from "primeng/api";
import {FormBuilder, FormGroup} from "@angular/forms";
import {Validation} from "../../../../@core/utils/models/validation";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-detail-classe',
  templateUrl: './detail-classe.component.html',
  styleUrls: ['./detail-classe.component.scss']
})
export class DetailClasseComponent implements OnInit, AfterViewInit {
  classeId:  string | null = "0" ;
  loading: boolean = false;
  validation :Validation = new Validation(""," ");
  responseMessage: ResponseMessage = new ResponseMessage([]);
  editClasseForm: FormGroup;
  classe: Classe = new Classe(undefined,undefined, undefined, undefined);
  index : number  = 0;
  constructor(private classeService: ClasseService, private messageService: MessageService, private formBuilder: FormBuilder, private cdRef: ChangeDetectorRef, private activatedRoute: ActivatedRoute) {
    this.editClasseForm = this.formBuilder.group(Classe.detailClasseValidation());
     if (this. displayIfEnseignant()){
       this.editClasseForm.disable()
     }
  }

  ngOnInit(): void {
    this.classeId = this.activatedRoute.snapshot.paramMap.get('id');
  }
  ngAfterViewInit() {

    this.loading = true;
    this.cdRef.detectChanges();
    this.responseMessage = new ResponseMessage([])
    this.classeService.getById(this.classeId).subscribe(value => {
        this.next(value);

      },
      error => {
        this.error(error)
      },
      () => {
        this.complete()
      });


  }
  displayIfEnseignant(){
    return localStorage.getItem("role") == "ENSEIGNANT";
  }
  next (value: Classe){
   this.classe = value
  }
  error (er: ResponseMessage){
    this.responseMessage = er;
    this.responseMessage.error(this.responseMessage, this.messageService)
    this.loading = false;
  }
  complete() {
    this.loading = false;
  }

  submit() {
    this.editClasseForm.disable();
    this.loading = true;
    this.classeService.updateClasse(this.classe, this.classe.id).subscribe(data => {
        this.responseMessage.success(data.message, '', this.messageService);
      }, (error: ResponseMessage) => {
        this.responseMessage = error;
        this.responseMessage.error(error, this.messageService);
        this.loading = false;
        this.editClasseForm.enable();
        this.editClasseForm.controls['id'].disable();
      },
      () => {
        this.loading = false;
        this.responseMessage = new ResponseMessage([]);
        this.loading = false;
        this.editClasseForm.enable();
        this.editClasseForm.controls['id'].disable();

      }
    );
  }

  handelTabViewChange(e: any) {
      this.index  = e.index;
  }

  displayIfAdmin() {
    return localStorage.getItem("role") == "ADMIN";

  }
}
