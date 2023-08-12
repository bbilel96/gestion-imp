import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {Classe} from "../../../../@core/models/classe/classe";
import {ResponseMessage} from "../../../../@core/utils/models/response-message";
import {MessageService} from "primeng/api";
import {ClasseService} from "../../../../@core/service/classe-service/classe.service";
import {Validation} from "../../../../@core/utils/models/validation";

@Component({
  selector: 'app-add-classe',
  templateUrl: './add-classe.component.html',
  styleUrls: ['./add-classe.component.scss']
})
export class AddClasseComponent implements OnInit {


  addClasse: FormGroup;
  submitButton: boolean = false;
  validation :Validation = new Validation(""," ");
  responseMessage: ResponseMessage = new ResponseMessage([]);
  constructor(private formBuilder: FormBuilder,
              private messageService: MessageService,
              private classeService: ClasseService) {
    this.addClasse = this.formBuilder.group(Classe.createClasseValidator());
  }

  ngOnInit(): void {
  }
   onSubmit(): void{

     this.addClasse.disable();
     this.submitButton = true;
     let classe: Classe = this.addClasse.getRawValue();

    this.classeService.createClasse(classe).subscribe(data => {


      this.responseMessage.success(data.message, '', this.messageService);
    }, (error: ResponseMessage) => {
      this.responseMessage = error;
        this.responseMessage.error(error, this.messageService);
        this.submitButton = false;
      this.addClasse.enable();
    },
      () => {
        this.submitButton = this.submitButton = false;
        this.responseMessage = new ResponseMessage([]);
        this.submitButton = false;
        this.addClasse.enable();
        this.addClasse.reset();
      }
   );
  }

}
