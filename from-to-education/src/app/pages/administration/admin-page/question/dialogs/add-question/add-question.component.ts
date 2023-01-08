import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';
import {RestService} from '../../../../../../services/rest.service';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {OkInformComponent} from '../../../../../../common/ok-inform/ok-inform.component';
import {environment} from "../../../../../../../environments/environment";

@Component({
  selector: 'app-add-question',
  templateUrl: './add-question.component.html',
  styleUrls: ['./add-question.component.css']
})
export class AddQuestionComponent implements OnInit {

  form: FormGroup;
  err = false;
  errText: string;
  public answerList = [];

  constructor(public dialogRef: MatDialogRef<AddQuestionComponent>,
              public restService: RestService,
              public dialog: MatDialog,
              @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit(): void {
    this.form =  new FormGroup({
      questionText: new FormControl(null, Validators.required),
      answerInformation: new FormControl(null, Validators.required)
    });
    this.answerList.push(new FormGroup({
      answerText: new FormControl(null, Validators.required),
      isRight: new FormControl(true, Validators.required)
    }));
  }

  onNoClick(): void {
    this.dialogRef.close(false);
  }

  addQuestion(): void{
    const list = [];
    for (const item of Object.keys(this.answerList)) {
      const temp = {
        answerText: this.answerList[item].get('answerText').value,
        isRight: this.answerList[item].get('isRight').value,
      };
      list.push(temp);
    }
    this.restService.post(environment.adminUrl, 'manage/question', {
      programId: this.data,
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

  check(item: FormGroup): void{
    this.answerList.forEach(val => {
      if (item !== val)
      { val.get('isRight').setValue(false); }
    });
  }

  addAnswer(): void{
    this.answerList.push(new FormGroup({
      answerText: new FormControl(null, Validators.required),
      isRight: new FormControl(false, Validators.required)
    }));
  }

  deleteAnswer(i: any): void{
    const index: number = this.answerList.indexOf(i);
    this.answerList.splice(index, 1);
  }

  checkParam(): boolean {
    let temp = false;
    for (const item of Object.keys( this.answerList)) {
      temp = (this.answerList[item].invalid || temp);
    }
    return temp;
  }
}
