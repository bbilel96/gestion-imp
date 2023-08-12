import {AfterViewInit, ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {Validation} from "../../../../@core/utils/models/validation";
import {ResponseMessage} from "../../../../@core/utils/models/response-message";
import {FormBuilder, FormGroup} from "@angular/forms";
import {Classe} from "../../../../@core/models/classe/classe";
import {ClasseService} from "../../../../@core/service/classe-service/classe.service";
import {MessageService} from "primeng/api";
import {MatiereService} from "../../../../@core/service/matiere-service/matiere.service";
import {Matiere} from "../../../../@core/models/matiere/matiere";
import {User} from "../../../../@core/models/user/user";
import {UserService} from "../../../../@core/service/user/user.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-detail-matiere',
  templateUrl: './detail-matiere.component.html',
  styleUrls: ['./detail-matiere.component.scss']
})
export class DetailMatiereComponent implements OnInit, AfterViewInit  {

  matiereId: string | undefined;
  listEnseignant: User[] = [];
  loading: boolean = false;
  validation :Validation = new Validation(""," ");
  responseMessage: ResponseMessage = new ResponseMessage([]);
  editMatiereForm: FormGroup;
  matiere: Matiere = new Matiere(undefined, undefined, [], undefined, undefined);
  index : number | undefined = 0;
  constructor(private matiereService: MatiereService, private activatedRoute: ActivatedRoute, private messageService: MessageService, private formBuilder: FormBuilder, private cdRef: ChangeDetectorRef, private userService: UserService) {
    this.editMatiereForm = this.formBuilder.group(Matiere.editMatiereValidator());
    if (this.displayIfEnseignant()){
      this.editMatiereForm.disable()

    }
    this.matiereId = this.activatedRoute.snapshot.paramMap.get('id');

  }

  ngOnInit(): void {
  }
  ngAfterViewInit() {
    this.loading = true;
    this.responseMessage = new ResponseMessage([])
    this.matiereService.getById(this.matiereId).subscribe(value => {
        this.next(value);

      },
      error => {
        this.error(error)
      },
      () => {
        this.complete()
      });
    this.userService.findAllWithoutPage().subscribe(value => {
        this.listEnseignant = value;

      },
      error => {
        this.error(error)
      },
      () => {
        this.complete()
      });

    this.cdRef.detectChanges();

  }
  next (value: Matiere){
    this.matiere = value
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
    this.editMatiereForm.disable();
    this.loading = true;
    this.matiere.enseignantId = this.matiere.enseignantDto?.id;
    console.log(this.matiere)
    this.matiereService.updateMatiere(this.matiere.id, this.matiere).subscribe(data => {
        this.responseMessage.success(data.message, '', this.messageService);
      }, (error: ResponseMessage) => {
        this.responseMessage = error;
        this.responseMessage.error(error, this.messageService);
        this.loading = false;
        this.editMatiereForm.enable();
        this.editMatiereForm.controls['id'].disable();
      },
      () => {
        this.loading = false;
        this.responseMessage = new ResponseMessage([]);
        this.loading = false;
        this.editMatiereForm.enable();
        this.editMatiereForm.controls['id'].disable();

      }
    );
  }
  displayIfEnseignant(){
    return localStorage.getItem("role") == "ENSEIGNANT";
  }
  handelTabViewChange(e: any) {
    this.index  = e.index;
  }

  displayIfAdmin() {
    return localStorage.getItem("role") == "ADMIN";

  }
}
