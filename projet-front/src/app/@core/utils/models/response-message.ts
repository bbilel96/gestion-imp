import {Validation} from "./validation";
import {Message, MessageService} from "primeng/api";

export class ResponseMessage {
  private _message: string | undefined;
  private _behavior: string | undefined;
  private _validations: Validation[] = [];
  private _status: number | undefined;


  constructor( validations: Validation[], response?: string, behavior?: string, status?: number) {
    this._message = response;
    this._behavior = behavior;
    this._validations = validations;
    this._status = status;

  }


  get status(): number | undefined{
    return this._status;
  }

  set status(value: number | undefined) {
    this._status = value;
  }

  get message(): string | undefined{
    return this._message;
  }

  set message(value: string | undefined) {
    this._message = value;
  }

  get behavior(): string | undefined{
    return this._behavior;
  }

  set behavior(value: string | undefined) {
    this._behavior = value;
  }

  get validations(): Validation[] {
    return this._validations;
  }

  set validations(value: Validation[]) {
    this._validations = value;
  }
  error( error: ResponseMessage,  messageService: MessageService | Message [],): void | Message[] {
    if (messageService instanceof MessageService) {
      messageService.clear();

      if (error.status != 422) {
        messageService.add({severity: 'error', summary: '', detail: error._message, sticky: true});
      }
    } else {
      if (error.status != 422) {
        return [{severity: 'error', summary: '', detail: error._message, sticky: true}]
      }


    }


  }

  success(message: string | undefined, initIcon: string, messageService: MessageService | Message[]): Message | void {


    if (messageService instanceof MessageService) {
      messageService.clear();
      messageService.add({severity: 'success', summary: 'Success', detail: message});
    } else {
      return {severity: 'success', summary: 'Success', detail: message}

    }
  }

}
