import {Component, Inject, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {RestService} from '../../../../../services/rest.service';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';
import {OkInformComponent} from '../../../../../common/ok-inform/ok-inform.component';
import {environment} from "../../../../../../environments/environment";

@Component({
  selector: 'app-add-program',
  templateUrl: './add-program.component.html',
  styleUrls: ['./add-program.component.css']
})
export class AddProgramComponent implements OnInit {

  form: FormGroup;
  err = false;
  errText: string;

  constructor(public dialogRef: MatDialogRef<AddProgramComponent>,
              public restService: RestService,
              public dialog: MatDialog,
              @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit(): void {
    this.form =  new FormGroup({
      name: new FormControl(null, Validators.required),
      description: new FormControl(null, Validators.required),
      minimalDuration: new FormControl(null, [Validators.required, Validators.min(1)]),
      completeTime: new FormControl(null,[ Validators.required, Validators.min(1)]),
      questionNums: new FormControl(null, [Validators.required, Validators.min(1)]),
      triesCount: new FormControl(null, [Validators.required, Validators.min(1)]),
      price: new FormControl(null, [Validators.required, Validators.min(1)])
    });
  }

  onNoClick(): void {
    this.dialogRef.close(false);
  }

  addProgram(): void{
    this.restService.post(environment.adminUrl, 'manage/program', {
      name: this.form.get('name').value,
      description: this.form.get('description').value,
      minimalDuration: this.form.get('minimalDuration').value,
      completeTime: this.form.get('completeTime').value,
      questionNums: this.form.get('questionNums').value,
      triesCount: this.form.get('triesCount').value,
      price: this.form.get('price').value,
      courseId: this.data,
    }).subscribe(
      result => {
        if (result.type === 'ok') {
          this.dialogRef.close(true);
          const dialogRef = this.dialog.open(OkInformComponent, {
            width: '380px',
            data: result.message
          });
          dialogRef.afterClosed().subscribe(result => {
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
