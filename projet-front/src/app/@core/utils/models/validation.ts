export class Validation {
  private _field: string;
  private _errorMessage: string;

  constructor(field: string, errorMessage: string) {
    this._field = field;
    this._errorMessage = errorMessage;
  }

  get field(): string {
    return this._field;
  }

  set field(value: string) {
    this._field = value;
  }

  get errorMessage(): string {
    return this._errorMessage;
  }

  set errorMessage(value: string) {
    this._errorMessage = value;
  }

   inputError (validations: Validation[], name: string): Validation[] {
    let result: Validation
    if (validations == null) {
      return [];
    }
    return validations.filter(validation =>
      validation.field == name
    );

  }
}
