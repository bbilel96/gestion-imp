import {CUSTOM_ELEMENTS_SCHEMA, NgModule} from '@angular/core';
import { CommonModule } from '@angular/common';
import {AvatarModule} from "primeng/avatar";
import {ButtonModule} from "primeng/button";
import {TabViewModule} from "primeng/tabview";
import {SkeletonModule} from "primeng/skeleton";
import {InputTextModule} from "primeng/inputtext";
import {ToastModule} from "primeng/toast";
import {InputTextareaModule} from "primeng/inputtextarea";
import {ConfirmationService, MessageService} from "primeng/api";
import {TableModule} from "primeng/table";
import {MenubarModule} from "primeng/menubar";
import {CardModule} from "primeng/card";
import {FileUploadModule} from 'primeng/fileupload';

import {DialogModule} from "primeng/dialog";
import {AvatarGroupModule} from "primeng/avatargroup";
import {ConfirmDialogModule} from "primeng/confirmdialog";
import {BreadcrumbModule} from "primeng/breadcrumb";
import {MessageModule} from "primeng/message";
import {RippleModule} from "primeng/ripple";
import {MegaMenuModule} from "primeng/megamenu";
import {MultiSelectModule} from "primeng/multiselect";
import {MessagesModule} from "primeng/messages";
import {PaginatorModule} from "primeng/paginator";
import {DividerModule} from 'primeng/divider';
import {AccordionModule} from "primeng/accordion";



@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    InputTextModule,
    ButtonModule,
    RippleModule,
    MenubarModule,
    MegaMenuModule,
    BreadcrumbModule,
    AvatarModule,
    AvatarGroupModule,
    FileUploadModule,
    CardModule,
    MessageModule,
    MessagesModule,
    TableModule,
    DialogModule,
    ToastModule,
    SkeletonModule,
    PaginatorModule,
    ConfirmDialogModule,
    InputTextareaModule,
    MultiSelectModule,
    TabViewModule,
    AccordionModule,
    DividerModule
  ],
  exports: [
    InputTextModule,
    ButtonModule,
    RippleModule,
    MenubarModule,
    MegaMenuModule,
    DialogModule,
    AvatarModule,
    BreadcrumbModule,
    AvatarGroupModule,
    CardModule,
    MessageModule,
    MessagesModule,
    TableModule,
    ToastModule,
    SkeletonModule,
    PaginatorModule,
    ConfirmDialogModule,
    InputTextareaModule,
    FileUploadModule,
    MultiSelectModule,
    TabViewModule,
    AccordionModule,
    DividerModule

  ],
  schemas: [
    CUSTOM_ELEMENTS_SCHEMA
  ],
  providers: [
    ConfirmationService,
    MessageService
  ]
})
export class PrimeNgModule { }
