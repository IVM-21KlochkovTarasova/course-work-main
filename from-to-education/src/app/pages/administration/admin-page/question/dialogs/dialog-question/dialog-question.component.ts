import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';
import {RestService} from '../../../../../../services/rest.service';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {OkInformComponent} from '../../../../../../common/ok-inform/ok-inform.component';
import {environment} from "../../../../../../../environments/environment";

@Component({
  selector: 'app-dialog-question',
  templateUrl: './dialog-question.component.html',
  styleUrls: ['./dialog-question.component.css']
})
export class DialogQuestionComponent implements OnInit {

  form: FormGroup;
  err = false;
  errText: string;
  public answerList = [];

  constructor(public dialogRef: MatDialogRef<DialogQuestionComponent>,
              public restService: RestService,
              public dialog: MatDialog,
              @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit(): void {
    this.form =  new FormGroup({
      questionText: new FormControl(this.data.questionText, Validators.required),
      answerInformation: new FormControl(this.data.answerInformation, Validators.required)
    });
    this.restService.get(environment.lkUrl, 'study/answers', {
      questionId: this.data.questionId,
    }).subscribe(
      result => {
          result.map(answer => {
            console.log(answer);
            this.answerList.push(new FormGroup({
              answerText: new FormControl(answer.answerText, Validators.required),
              isRight: new FormControl(answer.isRight, Validators.required),
              answerId: new FormControl(answer.answerId, Validators.required),
              delete: new FormControl(null)
            }));
          });
      }, err => {
      }
    );
  }

  check(item: FormGroup): void{
    this.answerList.forEach(val => {
      if (item !== val)
      { val.get('isRight').setValue(false); }
    });
  }

  addAnswer(): void{
    this.answerList.push(new FormGroup({
      answerText: new FormControl(null, Validators.required),
      isRight: new FormControl(null, Validators.required),
      answerId: new FormControl(null, Validators.required),
      delete: new FormControl(null, Validators.required)
    }));
  }

  deleteAnswer(answer: any): void{
    answer.get('delete').setValue(true);
  }

  checkParam(): boolean {
    let temp = false;
    for (const item of Object.keys( this.answerList)) {
      temp = (this.answerList[item].invalid || temp);
    }
    return temp;
  }

  onNoClick(): void {
    this.dialogRef.close(false);
  }

  save(): void{
    const list = [];
    for (const item of Object.keys(this.answerList)) {
      const temp = {
        answerText: this.answerList[item].get('answerText').value,
        isRight: this.answerList[item].get('isRight').value,
        answerId: this.answerList[item].get('answerId').value,
        delete: this.answerList[item].get('delete').value
      };
      list.push(temp);
    }
    this.restService.put(environment.adminUrl, 'manage/question', {
      questionId: this.data.questionId,
      answerList: list,
      questionText: this.form.get('questionText').value,
      answerInformation: this.form.get('answerInformation').value
    }).subscribe(
      result => {
        if (result.type === 'ok') {
          this.dialogRef.close(true);
          const dialogRef = this.dialog.open(OkInformComponent, {
            width: '380px',
            data: result.message
          });
          dialogRef.afterClosed().subscribe(res => {
            console.log('The dialog was closed');
          });
        } else if (result.type === 'error') {
          this.err = true;
          this.errText = result.message;
        }
      }, err => {
      }
    );
  }

}
