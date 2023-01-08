import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {RestService} from '../../../../services/rest.service';
import {MatDialog} from '@angular/material/dialog';
import {environment} from '../../../../../environments/environment';
import {AddQuestionComponent} from '../question/dialogs/add-question/add-question.component';
import {DialogLiterComponent} from './dialog-liter/dialog-liter.component';
import {InfoDialogComponent} from '../../../../common/info-dialog/info-dialog.component';
import {OkInformComponent} from '../../../../common/ok-inform/ok-inform.component';

@Component({
  selector: 'app-add-literature',
  templateUrl: './add-literature.component.html',
  styleUrls: ['./add-literature.component.css']
})
export class AddLiteratureComponent implements OnInit {

  literatures: any;
  textSearch: string = "";
  @Input() idProgram: number;
  constructor(private router: Router,
              public restService: RestService,
              public dialog: MatDialog) { }

  ngOnInit(): void {
    this.loadLiterature();
  }

  search(): void{
    this.loadLiterature();
  }

  loadLiterature(): void{
    this.restService.get(environment.lkUrl, 'study/literature', {
      studyProgramId: this.idProgram,
      filter: this.textSearch
    }).subscribe(
      result => {
        this.literatures = result;
        this.literatures.map( l => {
          l.status = false;
        });
      }, error => {
      }
    );
  }

  openLink(link: string): void {
    window.open(link);
  }

  clickEvent(lit): void{
    lit.status = !lit.status;
  }

  deletelit(item: any): void{
    const dialogRef = this.dialog.open(InfoDialogComponent, {
      width: '380px',
      data: 'Вы действиельно хотите удалить источник?',
      autoFocus: false,
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      if (result) {
        this.restService.delete(environment.adminUrl, 'manage/literature', {
          studyProgramLiteratureId: item.studyProgramLiteratureId
        }).subscribe(res => {
            if (res.type === 'ok') {
              const dialogRef1 = this.dialog.open(OkInformComponent, {
                width: '380px',
                data: res.message
              });
              dialogRef1.afterClosed().subscribe(res1 => {
                console.log('The dialog was closed');
              });
              this.loadLiterature();
            }
          }, err => {
          }
        );
      }
    });
  }

  addLiter(): void{
    const dialogRef = this.dialog.open(DialogLiterComponent, {
      width: '660px',
      autoFocus: false,
      data: this.idProgram
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      if (result) {
        this.loadLiterature();
      }
    });
  }


}
