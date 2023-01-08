import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {RestService} from '../../../../services/rest.service';
import {AddNewSectionComponent} from '../dialogs/add-new-section/add-new-section.component';
import {MatDialog} from '@angular/material/dialog';
import {AddQuestionComponent} from './dialogs/add-question/add-question.component';
import {DialogQuestionComponent} from './dialogs/dialog-question/dialog-question.component';
import {InfoDialogComponent} from '../../../../common/info-dialog/info-dialog.component';
import {OkInformComponent} from '../../../../common/ok-inform/ok-inform.component';
import {environment} from "../../../../../environments/environment";

@Component({
  selector: 'app-question',
  templateUrl: './question.component.html',
  styleUrls: ['./question.component.css']
})
export class QuestionComponent implements OnInit {

  @Input() idProgram: number;
  questions: any = [];
  constructor(private router: Router,
              public restService: RestService,
              public dialog: MatDialog,
              private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.loadQuestion();
  }

  loadQuestion(): void{
    this.restService.get(environment.lkUrl, 'study/question', {
      studyProgramId: this.idProgram
    }).subscribe(result => {
        console.log(result);
        this.questions = result;
      }, err => {
      }
    );
  }

  newQuestion(): void{
    const dialogRef = this.dialog.open(AddQuestionComponent, {
      width: '660px',
      autoFocus: false,
      data: this.idProgram
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      if (result) {
        this.loadQuestion();
      }
    });
  }

  detail(question: any): void{
    const dialogRef = this.dialog.open(DialogQuestionComponent, {
      width: '660px',
      autoFocus: false,
      data: question
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      if (result) {
        this.loadQuestion();
      }
    });
  }

  delete(question: any): void{
    const dialogRef = this.dialog.open(InfoDialogComponent, {
      width: '380px',
      data: 'Вы действиельно хотите удалить вопрос?',
      autoFocus: false,
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      if (result) {
        this.restService.delete(environment.adminUrl, 'manage/question', {
          questionId: question.questionId
        }).subscribe(res => {
            const dialogRef1 = this.dialog.open(OkInformComponent, {
              width: '380px',
              data: res.message
            });
            dialogRef1.afterClosed().subscribe(res2 => {
              console.log('The dialog was closed');
            });
            this.loadQuestion();
          }, err => {
          }
        );
      }
    });
  }

}
