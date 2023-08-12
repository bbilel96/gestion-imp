import { Component, OnInit } from '@angular/core';
import {Form, FormBuilder, FormGroup} from "@angular/forms";
import {User} from "../../@core/models/user/user";
import {UserService} from "../../@core/service/user/user.service";
import {ResponseMessage} from "../../@core/utils/models/response-message";
import {Validation} from "../../@core/utils/models/validation";
import {MessageService} from "primeng/api";
import {Router} from "@angular/router";

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.scss']
})
export class AuthComponent implements OnInit {

  login: FormGroup;
  submitButton: boolean = false;
  validation :Validation = new Validation(""," ");
  responseMessage: ResponseMessage = new ResponseMessage([]);
  userId: string | undefined | null;
  constructor(private formBuilder: FormBuilder, private userService: UserService, private messageService: MessageService, private router: Router) {
    this.login = this.formBuilder.group(User.loginValidator())
  }

  ngOnInit(): void {
  }

  submit() {
    let user: User = this.login.getRawValue();
    this.userService.login(user).subscribe(data => {


      },
      (error: ResponseMessage) => {
        this.responseMessage = error;
        this.responseMessage.error(error, this.messageService);
        this.submitButton = false;
        this.login.enable();
      },
      () => {
        this.submitButton = this.submitButton = false;
        this.responseMessage = new ResponseMessage([]);
        this.submitButton = false;
        this.login.enable();
        this.login.reset();
        localStorage.getItem("token")
        this.router.navigate(["/main/tirage"])
      }

  )
  }
}
