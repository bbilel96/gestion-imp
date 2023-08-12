import {AfterViewInit, Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {User} from "../../../../@core/models/user/user";
import {Validation} from "../../../../@core/utils/models/validation";
import {ResponseMessage} from "../../../../@core/utils/models/response-message";
import {EnseignantService} from "../../../../@core/service/user/enseignant/enseignant.service";
import {TirageAgentService} from "../../../../@core/service/user/tirage/tirage-agent.service";
import {AdminService} from "../../../../@core/service/user/admin/admin.service";
import {MessageService} from "primeng/api";
import {DatePipe} from "@angular/common";
import {UserService} from "../../../../@core/service/user/user.service";

@Component({
  selector: 'app-update-user',
  templateUrl: './update-user.component.html',
  styleUrls: ['./update-user.component.scss']
})
export class UpdateUserComponent implements OnInit, AfterViewInit {
  editUser: FormGroup;
  submitButton: boolean = false;
  validation :Validation = new Validation(""," ");
  responseMessage: ResponseMessage = new ResponseMessage([]);
  showTypeError: boolean = false;
  @Input() userId: string | undefined;
  foundedUser: User = new User(undefined, undefined, null, undefined, undefined, undefined, undefined, undefined, undefined, undefined);

  constructor(private formBuilder: FormBuilder,
              private enseignantService: EnseignantService,
              private userService: UserService,
              private tirageAgentService: TirageAgentService,
              private adminService: AdminService,
              private messageService: MessageService,
              private datePipe: DatePipe) {
    this.editUser = this.formBuilder.group(User.editUserValidator())
  }

  ngOnInit(): void {
  }

  ngAfterViewInit(): void {
    this.userService.findById(this.userId).subscribe(value => {
      this.foundedUser = value
      console.log(value.dateNaissance)
      if (value.dateNaissance != null) {
        this.foundedUser.dateNaissance = new Date(value.dateNaissance)
      }
    },
      error1 => this.error(error1),
      () => this.finish());
  }
  onSubmit() {
    this.editUser.disable();
    this.submitButton = true;
    this.showTypeError = false;
    let user: User = this.editUser.getRawValue();

    user.dateNaissance =this.datePipe.transform(user.dateNaissance, "yyyy-MM-dd");
    user.typeUser = user.typeUser?.toUpperCase();

      this.userService.updateUser(this.userId, user).subscribe(data => {
          this.success(data)
        }, (error: ResponseMessage) => {
          this.error(error)
        },
        () => {
          this.finish()
        }
      );
  }
  success(data: ResponseMessage) : void{
    this.responseMessage.success(data.message, '', this.messageService);
  }
  error(error: ResponseMessage): void{
    this.responseMessage = error;
    this.responseMessage.error(error, this.messageService);
    this.submitButton = false;
    this.editUser.enable();
    this.editUser.controls['id'].disable();
    this.editUser.controls['email'].disable();
  }
  finish(): void {
    this.submitButton = this.submitButton = false;
    this.responseMessage = new ResponseMessage([]);
    this.submitButton = false;
    this.editUser.enable();
    this.editUser.controls['id'].disable();
    this.editUser.controls['email'].disable();
  }


}
