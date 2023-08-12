import {AfterViewInit, ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {Matiere} from "../../../../@core/models/matiere/matiere";
import {Validation} from "../../../../@core/utils/models/validation";
import {Classe} from "../../../../@core/models/classe/classe";
import {ResponseMessage} from "../../../../@core/utils/models/response-message";
import {Tirage} from "../../../../@core/models/tirage/tirage";
import {TirageService} from "../../../../@core/service/tirage-service/tirage.service";
import {DatePipe} from "@angular/common";
import {MatiereService} from "../../../../@core/service/matiere-service/matiere.service";
import {MessageService} from "primeng/api";
import {ClasseService} from "../../../../@core/service/classe-service/classe.service";
import {HttpEvent, HttpEventType} from "@angular/common/http";
import { saveAs } from 'file-saver';
import * as http from "http";
import {ActivatedRoute} from "@angular/router";


@Component({
  selector: 'app-detail-tirage',
  templateUrl: './detail-tirage.component.html',
  styleUrls: ['./detail-tirage.component.scss']
})
export class DetailTirageComponent implements OnInit, AfterViewInit {

  editTirageForm: FormGroup;
  affectedMatiere: Matiere[] = [];
  submitButton: boolean = false;
  uploadedFiles: any;
  validation: Validation = new Validation("", "");
  tirageId: string | undefined ;
  enseignantId: string | undefined = "U_52";
  selectedTirage: Tirage = new Tirage(null);
  affectClasse: Classe[] = [];
  loading: boolean = false;
  fileStatus = {status: '', requestType: '', percent: 0};
  responseMessage: ResponseMessage = new ResponseMessage([]);
  tirageType: String[] = ["IMPRIMER", "PAS_ENCORE", "EN_COURS"];
  disableTirage = false;

  constructor(private datePipe: DatePipe,
              private formBuilder: FormBuilder,
              private matiereService: MatiereService,
              private messageService: MessageService,
              private tirageService: TirageService,
              private classeService: ClasseService,
              private cdRef: ChangeDetectorRef,
              private activatedRoute: ActivatedRoute) {
    this.editTirageForm = this.formBuilder.group(Tirage.editTirageValidator())
    if (localStorage.getItem("role") == "AGENT"){
      this.editTirageForm.disable();
      this.disableTirage = true;
      this.editTirageForm.controls['status'].enable();
    }
    if (localStorage.getItem("role") == "ENSEIGNANT"){
      this.editTirageForm.controls['status'].disable();
    }
    if (localStorage.getItem("role") == "ADMIN"){
      this.editTirageForm.disable();
      this.disableTirage = true;
    }

    this.tirageId = this.activatedRoute.snapshot.paramMap.get('id');
    this.enseignantId = localStorage.getItem("id");
  }

  ngOnInit(): void {
  }

  ngAfterViewInit() {
    this.getAllMatiereByEnseignantIdWithoutPage();

  }

  private getAllMatiereByEnseignantIdWithoutPage() {
    this.loading = true;

    this.matiereService.getAllMatiereByEnseignantId(this.enseignantId).subscribe(value =>
        this.next(value),
      error1 => this.error(error1),
      () => {
        this.complete();
        this.getTirageByIdWithAllData();
      });


  }

  onSelect($event: any) {
    this.uploadedFiles = $event.files[0];
    console.log(this.uploadedFiles)
  }

  getTirageByIdWithAllData() {
    this.tirageService.getTirageWithAllData(this.tirageId).subscribe(value => {
        this.selectedTirage = value;
        if (localStorage.getItem("role") == "ENSEIGNANT"){
          if (this.selectedTirage.status == "IMPRIMER"){
            this.editTirageForm.disable();
            this.disableTirage = true
          }
        }


        this.selectedTirage.dateImpression = new Date(value.dateImpression!)

      },
      error1 => this.error(error1),
      () => {
        this.complete();
        this.findAllByMatiereIdWithoutPage(this.selectedTirage.matiereDto?.id);
      });


  }

  next(value: Matiere[]) {
    this.affectedMatiere = value;
  }

  nextClasse(value: Classe[]) {
    this.affectClasse = value;
    this.cdRef.detectChanges();
  }

  error(er: ResponseMessage) {
    this.responseMessage = er;
    this.responseMessage.error(this.responseMessage, this.messageService)
    this.loading = false;

  }

  complete() {
    this.loading = false;

  }
  onDownloadFile(filename: string): void {


      this.tirageService.download(this.tirageId, filename).subscribe(
        event => {
          console.log(event);
          this.resportProgress(event)


        },
        (error: ResponseMessage) => {
          this.error(error);
        },
        () => this.complete()
      );


  }
  checkIfUpdateButtonShouldBeDisabled(){
   //console.log(this.submitButton || this.selectedTirage.documentPdf == undefined || localStorage.getItem("role") == "ENSEIGNANT")
    return this.submitButton || this.selectedTirage.documentPdf == undefined  || (localStorage.getItem("role") == "ENSEIGNANT" && this.selectedTirage.status == "IMPRIMER") || localStorage.getItem("role") == "ADMIN"  ;
  }
  miseAJourClasse($event: any) {
    this.findAllByMatiereIdWithoutPage($event.value.id);

  }

  private findAllByMatiereIdWithoutPage(id: string | undefined) {
    this.loading = true;
    this.classeService.getClasseeByMatiereWithoutPage(id).subscribe(value =>
        this.nextClasse(value),
      error1 => this.error(error1),
      () => this.complete());

    this.cdRef.detectChanges();
  }

  private resportProgress(httpEvent: HttpEvent<ResponseMessage | Blob>): void {
    switch (httpEvent.type) {
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
        if ( httpEvent.body instanceof ResponseMessage && httpEvent.body.message != undefined){
          this.responseMessage.success("tirage est modifier avec succes", '', this.messageService);
        }
          else if (httpEvent.body instanceof Blob){

            saveAs(new File([httpEvent.body!], this.selectedTirage.documentPdf,
              {type: `${httpEvent.headers.get('Content-Type')};charset=utf-8`}));
          }
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

    this.selectedTirage.documentPdf = undefined;
  }

  onSubmit() {
    console.log(this.uploadedFiles)
    this.editTirageForm.disable();
    this.submitButton = true;
    this.loading = true;
    let tirage: Tirage = this.editTirageForm.getRawValue();
    console.log(tirage)
    if (localStorage.getItem("role") == "AGENT") {
      this.tirageService.changeStatus(tirage.id, localStorage.getItem("id"), tirage.status).subscribe((data) => {
          this.responseMessage.success(data.message, '', this.messageService);
        }, (error: ResponseMessage) => {
          this.responseMessage = error;
          this.responseMessage.error(error, this.messageService);
          this.editTirageForm.disable();
          this.disableTirage = true;
          this.editTirageForm.controls['status'].enable();
        },
        () => {
          this.submitButton = false;
          this.responseMessage = new ResponseMessage([] );
          this.editTirageForm.disable();
          this.disableTirage = true;
          this.editTirageForm.controls['status'].enable();
        }
      );
    }
     else if (localStorage.getItem("role") == "ENSEIGNANT") {
      console.log(this.uploadedFiles)
      this.editTirageForm.disable();
      this.submitButton = true;
      this.loading = true;
      let tirage: Tirage = this.editTirageForm.getRawValue();
      console.log(tirage)
      tirage.dateImpression = this.datePipe.transform(tirage.dateImpression, "yyyy-MM-dd HH:mm");
      let classe: Classe = this.editTirageForm.getRawValue().classeDto;
      let formData: FormData = new FormData();
      tirage.matiereId = tirage.matiereDto?.id;
      tirage.enseignantId = this.enseignantId;
      tirage.classeId = tirage.classeDto?.id;
      console.log(tirage);
      formData.append("pdf", this.uploadedFiles)
      formData.append('tirage', new Blob([JSON.stringify(tirage)], {
        type: 'application/json'
      }));
      console.log("fffffls;flze;fgp,ze,gkp")
      this.tirageService.updateTirage(tirage.id, formData).subscribe((data) => {
          console.log(data)
          this.resportProgress(data);
        }, (error: ResponseMessage) => {
          this.responseMessage = error;
          this.responseMessage.error(error, this.messageService);
          this.submitButton = false;
          this.editTirageForm.enable();
          this.editTirageForm.controls['id'].disable();
        },
        () => {
          this.submitButton = false;
          this.responseMessage = new ResponseMessage([] );
          this.submitButton = false;
          this.editTirageForm.enable();
          this.editTirageForm.controls['id'].disable();
        }
      );
    }
  }
}
