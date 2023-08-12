import {ChangeDetectorRef, Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {Matiere} from "../../../../@core/models/matiere/matiere";
import {ResponseMessage} from "../../../../@core/utils/models/response-message";
import {FormBuilder, FormGroup} from "@angular/forms";
import {MatiereService} from "../../../../@core/service/matiere-service/matiere.service";
import {MessageService} from "primeng/api";
import {ClasseService} from "../../../../@core/service/classe-service/classe.service";
import {Classe} from "../../../../@core/models/classe/classe";

@Component({
  selector: 'app-affect-classe',
  templateUrl: './affect-classe.component.html',
  styleUrls: ['./affect-classe.component.scss']
})
export class AffectClasseComponent implements OnInit, OnChanges {


  listClasse: Classe[] = [];
  responseMessage: ResponseMessage = new ResponseMessage([]);
  loading: boolean = false;
  @Input() matiereId: string | undefined;
  affectClasseForm: FormGroup;
  affectedClasse: Classe[] = [];
  @Input() index : number | undefined = 0;

  constructor(private matiereService: MatiereService, private messageService: MessageService, private formBuilder: FormBuilder, private classeService: ClasseService, private cdRef: ChangeDetectorRef) {
    this.affectClasseForm = this.formBuilder.group(Matiere.affectClasseValidator());
  }

  ngOnInit(): void {
  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['index'].currentValue == 1) {
      this.classeService.getAllWithoutPage().subscribe(data => {
          this.next(data);
        }, error1 => this.error(error1),
        () => this.complete())
    }
  }


  getMatiereByClasse(matiereId: string | undefined) {
    this.loading = true;

    this.responseMessage = new ResponseMessage([])
    this.classeService.getClasseeByMatiereWithoutPage(matiereId).subscribe(value => {
        this.nextAffect(value);

      },
      error => {
        this.error(error)
      },
      () => {
        this.loading = false;


      });
    this.cdRef.detectChanges();
  }

  nextAffect(value: Classe[]) {
    this.affectedClasse = value;
    console.log(value);
    console.log(this.affectedClasse)
  }

  next(value: Classe[]) {


    this.listClasse = value;

  }

  error(er: ResponseMessage) {
    console.log(er)
    this.responseMessage = er;
    this.responseMessage.error(this.responseMessage, this.messageService)
    this.loading = false;
  }

  complete() {
    this.loading = false;
    this.getMatiereByClasse(this.matiereId);
  }

  submit() {
    this.loading = true;
    this.affectClasseForm.disable();
    let selectedClasse: Matiere[] = this.affectClasseForm.get('classes')?.value;
    this.matiereService.affectClasse(this.matiereId, selectedClasse).subscribe(value => {
        this.responseMessage.success(value.message, '', this.messageService);
      }, (error: ResponseMessage) => {
        this.responseMessage = error;
        this.responseMessage.error(error, this.messageService);
        this.loading = false;
        this.affectClasseForm.enable();

      },
      () => {
        this.loading = false;
        this.responseMessage = new ResponseMessage([]);
        this.loading = false;
        this.affectClasseForm.enable();

      });
  }
}
