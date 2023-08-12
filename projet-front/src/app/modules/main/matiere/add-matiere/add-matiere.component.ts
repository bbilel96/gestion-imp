import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {Matiere} from "../../../../@core/models/matiere/matiere";
import {Validation} from "../../../../@core/utils/models/validation";
import {ResponseMessage} from "../../../../@core/utils/models/response-message";
import {Classe} from "../../../../@core/models/classe/classe";
import {MatiereService} from "../../../../@core/service/matiere-service/matiere.service";
import {MessageService} from "primeng/api";

@Component({
  selector: 'app-add-matiere',
  templateUrl: './add-matiere.component.html',
  styleUrls: ['./add-matiere.component.scss']
})
export class AddMatiereComponent implements OnInit {

  addMatiere: FormGroup;
  submitButton: boolean = false;
  validation :Validation = new Validation(""," ");
  responseMessage: ResponseMessage = new ResponseMessage([]);
  constructor(private formBuilder: FormBuilder, private matiereService: MatiereService, private messageService: MessageService) {
    this.addMatiere = this.formBuilder.group(Matiere.createMatiereValidator());
  }

  ngOnInit(): void {
  }

  onSubmit() {
    this.addMatiere.disable();
    this.submitButton = true;
    let matiere: Matiere = this.addMatiere.getRawValue();
    console.log(matiere)
    this.matiereService.addMatiere(matiere).subscribe(data => {


        this.responseMessage.success(data.message, '', this.messageService);
      }, (error: ResponseMessage) => {
        this.responseMessage = error;
        this.responseMessage.error(error, this.messageService);
        this.submitButton = false;
        this.addMatiere.enable();
      },
      () => {
        this.submitButton = this.submitButton = false;
        this.responseMessage = new ResponseMessage([]);
        this.submitButton = false;
        this.addMatiere.enable();
        this.addMatiere.reset();
      }
    );
  }
}
