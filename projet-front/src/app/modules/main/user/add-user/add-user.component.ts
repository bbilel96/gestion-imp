import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {User} from "../../../../@core/models/user/user";
import {Validation} from "../../../../@core/utils/models/validation";
import {ResponseMessage} from "../../../../@core/utils/models/response-message";
import {EnseignantService} from "../../../../@core/service/user/enseignant/enseignant.service";
import {TirageAgentService} from "../../../../@core/service/user/tirage/tirage-agent.service";
import {AdminService} from "../../../../@core/service/user/admin/admin.service";
import {MessageService} from "primeng/api";
import {DatePipe} from "@angular/common";

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
  styleUrls: ['./add-user.component.scss']
})
export class AddUserComponent implements OnInit {

  addUser: FormGroup;
  submitButton: boolean = false;
  validation: Validation = new Validation("", " ");
  responseMessage: ResponseMessage = new ResponseMessage([]);
  showTypeError: boolean = false;
  userType: String[] = ["Enseignant", "Agent", "Admin"];

  constructor(private formBuilder: FormBuilder,
              private enseignantService: EnseignantService,
              private tirageAgentService: TirageAgentService,
              private adminService: AdminService,
              private messageService: MessageService,
              private datePipe: DatePipe) {
    this.addUser = this.formBuilder.group(User.addUserValidator())
  }

  ngOnInit(): void {
  }

  onSubmit() {
    this.addUser.disable();
    this.submitButton = true;
    this.showTypeError = false;
    let user: User = this.addUser.getRawValue();

    user.dateNaissance = this.datePipe.transform(user.dateNaissance, "yyyy-MM-dd");
    user.typeUser = user.typeUser?.toUpperCase();
    if (user.typeUser == "AGENT") {
      this.tirageAgentService.createAgentTirage(user).subscribe(data => {
          this.success(data)
        }, (error: ResponseMessage) => {
          this.error(error)
        },
        () => {
          this.finish()
        }
      );
    } else if (user.typeUser == "ADMIN") {
      this.adminService.createAdmin(user).subscribe(data => {
          this.success(data)
        }, (error: ResponseMessage) => {
          this.error(error)
        },
        () => {
          this.finish();
        }
      );
    } else if (user.typeUser == "ENSEIGNANT") {
      this.enseignantService.createEnseignant(user).subscribe(data => {
          this.success(data)
        }, (error: ResponseMessage) => {
          this.error(error)
        },
        () => {
          this.finish()
        }
      );
    } else {
      this.showTypeError = true;
      this.finish()
    }


  }

  success(data: ResponseMessage): void {
    this.responseMessage.success(data.message, '', this.messageService);
  }

  error(error: ResponseMessage): void {
    this.responseMessage = error;
    this.responseMessage.error(error, this.messageService);
    this.submitButton = false;
    this.addUser.enable();
  }

  finish(): void {
    this.submitButton = this.submitButton = false;
    this.responseMessage = new ResponseMessage([]);
    this.submitButton = false;
    this.addUser.enable();
    this.addUser.reset();
  }
}
