import {AfterViewInit, Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {Tirage} from "../../../../@core/models/tirage/tirage";
import {Validation} from "../../../../@core/utils/models/validation";
import {ResponseMessage} from "../../../../@core/utils/models/response-message";
import {MatiereService} from "../../../../@core/service/matiere-service/matiere.service";
import {Matiere} from "../../../../@core/models/matiere/matiere";
import {MessageService} from "primeng/api";
import {TirageService} from "../../../../@core/service/tirage-service/tirage.service";
import {Classe} from "../../../../@core/models/classe/classe";
import {ClasseService} from "../../../../@core/service/classe-service/classe.service";
import {DatePipe} from "@angular/common";
import {HttpEvent, HttpEventType} from "@angular/common/http";
import * as http from "http";

@Component({
  selector: 'app-add-tirage',
  templateUrl: './add-tirage.component.html',
  styleUrls: ['./add-tirage.component.scss']
})
export class AddTirageComponent implements OnInit, AfterViewInit {

  addTirageForm: FormGroup;
  affectedMatiere: Matiere[] = [];
  submitButton: boolean = false;
  uploadedFiles: any;
  validation :Validation = new Validation(""," ");
  enseignantId: string | undefined = "U_52";
  affectClasse: Classe[] = [];
  loading: boolean = false;
  fileStatus = { status: '', requestType: '', percent: 0 };
  responseMessage: ResponseMessage = new ResponseMessage([]);
  constructor(private datePipe: DatePipe,
              private formBuilder: FormBuilder,
              private matiereService: MatiereService,
              private messageService: MessageService,
              private tirageService: TirageService,
              private classeService: ClasseService) {
    this.addTirageForm = this.formBuilder.group(Tirage.createTirageValidator())
    this.enseignantId = localStorage.getItem("id")
  }

  ngOnInit(): void {
  }

  onSubmit() {
    console.log(this.uploadedFiles)
    this.addTirageForm.disable();
    this.submitButton = true;
    this.loading = true;
    let tirage: Tirage = this.addTirageForm.getRawValue();
    console.log(tirage)
    tirage.dateImpression = this.datePipe.transform(tirage.dateImpression, "yyyy-MM-dd HH:mm");
    let  classe: Classe = this.addTirageForm.getRawValue().classeDto;

    let formData: FormData = new FormData();
    tirage.matiereId = tirage.matiereDto?.id;
    tirage.classeId = tirage.classeDto?.id;
    tirage.enseignantId = this.enseignantId;
    console.log(tirage);
    formData.append("pdf", this.uploadedFiles)
    formData.append('tirage', new Blob([JSON.stringify(tirage)], {
      type: 'application/json'
    }));
    console.log("ffff")
    this.tirageService.addTirage(this?.enseignantId, classe?.id, formData).subscribe((data) => {
      console.log(data)
       this.resportProgress(data);
      }, (error: ResponseMessage) => {
        this.responseMessage = error;
        this.responseMessage.error(error, this.messageService);
        this.submitButton = false;
        this.addTirageForm.enable();
      },
      () => {
        this.submitButton = false;
        this.responseMessage = new ResponseMessage([]);
        this.submitButton = false;
        this.addTirageForm.enable();
        this.addTirageForm.reset();
      }
    );
  }
  onSelect($event: any)
  { this.uploadedFiles = $event.files[0];
    console.log(this.uploadedFiles)
  }
  ngAfterViewInit(): void {
    this.getAllMatiereByEnseignantIdWithoutPage(this.enseignantId);
  }

  private getAllMatiereByEnseignantIdWithoutPage(enseignantId: String | undefined) {
    this.loading = true;
    this.matiereService.getAllMatiereByEnseignantId(this.enseignantId).subscribe(value =>
      this.next(value),
      error1 => this.error(error1),
      () => this.complete());
  }
  private findAllByMatiereIdWithoutPage(id: string) {
    console.log(this.addTirageForm.getRawValue())
    this.loading = true;
    this.classeService.getClasseeByMatiereWithoutPage(id).subscribe(value =>
        this.nextClasse(value),
      error1 => this.error(error1),
      () => this.complete());
  }

  next (value: Matiere[]){
    this.affectedMatiere = value;
  }
  nextClasse (value: Classe[]){
    this.affectClasse = value;
  }
  error (er: ResponseMessage){
    this.responseMessage = er;
    this.responseMessage.error(this.responseMessage, this.messageService)
    this.loading = false;

  }
  complete() {
    this.loading = false;
  }

  miseAJourClasse($event: any) {
    console.log($event.value.id)
    this.findAllByMatiereIdWithoutPage($event.value.id);

  }
  private resportProgress(httpEvent: HttpEvent<ResponseMessage>): void {
    switch(httpEvent.type) {
      case HttpEventType.UploadProgress:
        this.updateStatus(httpEvent.loaded, httpEvent.total!, 'Uploading... ');
        break;
      case HttpEventType.DownloadProgress:
        this.updateStatus(httpEvent.loaded, httpEvent.total!, 'Downloading... ');
        break;
      case HttpEventType.ResponseHeader:
        console.log('Header returned', httpEvent);
        break;
      case HttpEventType.Response:
        this.responseMessage.success(httpEvent.body?.message, '', this.messageService);

        this.fileStatus.status = 'done';
        break;
      default:
        console.log(httpEvent);
        break;

    }
  }
  private updateStatus(loaded: number, total: number, requestType: string): void {
    this.fileStatus.status = 'progress';
    this.fileStatus.requestType = requestType;
    this.fileStatus.percent = Math.round(100 * loaded / total);
  }

  onRemove($event: any) {

    this.uploadedFiles = null
  }
}
