import {AfterViewInit, ChangeDetectorRef, Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {MatiereService} from "../../../../@core/service/matiere-service/matiere.service";
import {Matiere} from "../../../../@core/models/matiere/matiere";
import {ResponseMessage} from "../../../../@core/utils/models/response-message";
import {MessageService} from "primeng/api";
import {FormBuilder, FormGroup} from "@angular/forms";
import {Classe} from "../../../../@core/models/classe/classe";
import {ClasseService} from "../../../../@core/service/classe-service/classe.service";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-affect-matiere',
  templateUrl: './affect-matiere.component.html',
  styleUrls: ['./affect-matiere.component.scss']
})
export class AffectMatiereComponent implements OnInit,OnChanges, AfterViewInit {

  listMatieres: Matiere[] = [];
  responseMessage: ResponseMessage = new ResponseMessage([]);
  loading: boolean = false;
   classeId: string | undefined;
  affecterMatiereForm: FormGroup;
  affectedMatiere: Matiere[] = [];
  @Input() index: number | undefined;

  constructor(private matiereService: MatiereService, private activatedRoute: ActivatedRoute, private messageService: MessageService, private formBuilder: FormBuilder, private classeService: ClasseService, private cdRef: ChangeDetectorRef) {
    this.affecterMatiereForm = this.formBuilder.group(Classe.affecterMatiereValidator());
  }

  ngOnInit(): void {
  }
  ngAfterViewInit() {
    this.classeId = this.activatedRoute.snapshot.paramMap.get('id');

  }

  ngOnChanges(changes: SimpleChanges) {
    if (changes['index'].currentValue == 1){
      this.matiereService.getAllWithoutPage().subscribe(data => {
          this.next(data);
        }, error1 => this.error(error1),
        () => this.complete())
    }
  }


  getMatiereByClasse(classeId: string | undefined) {
    this.loading = true;

    this.responseMessage = new ResponseMessage([])
    this.matiereService.getMatiereByClasseWithoutPage(classeId).subscribe(value => {
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

  nextAffect(value: Matiere[]){
    this.affectedMatiere = value;
  }
  next(value: Matiere[]) {
    this.listMatieres = value;
  }

  error(er: ResponseMessage) {
    this.responseMessage = er;
    this.responseMessage.error(this.responseMessage, this.messageService)
    this.loading = false;
  }

  complete() {
    this.loading = false;
    this.getMatiereByClasse(this.classeId);
  }

  submit() {
    this.loading = true;
    this.affecterMatiereForm.disable();
    let selectedMatiere: Matiere[] = this.affecterMatiereForm.get('matieres')?.value;
    this.classeService.affectMatiere(this.classeId, selectedMatiere).subscribe(value => {
        this.responseMessage.success(value.message, '', this.messageService);
      }, (error: ResponseMessage) => {
        this.responseMessage = error;
        this.responseMessage.error(error, this.messageService);
        this.loading = false;
        this.affecterMatiereForm.enable();

      },
      () => {
        this.loading = false;
        this.responseMessage = new ResponseMessage([]);
        this.loading = false;
        this.affecterMatiereForm.enable();

    });
  }
}
