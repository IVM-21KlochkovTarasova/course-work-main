import {Component, Inject, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';
import {RestService} from '../../../../../services/rest.service';
import {OkInformComponent} from '../../../../../common/ok-inform/ok-inform.component';
import {AuthService} from '../../../../../services/auth.service';
import {environment} from "../../../../../../environments/environment";

@Component({
  selector: 'app-edit-program',
  templateUrl: './edit-program.component.html',
  styleUrls: ['./edit-program.component.css']
})
export class EditProgramComponent implements OnInit {

  form: FormGroup;
  err = false;
  errText: string;
  permission = false;

  constructor(public dialogRef: MatDialogRef<EditProgramComponent>,
              public restService: RestService,
              public dialog: MatDialog,
              public authService: AuthService,
              @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit(): void {
    this.authService.getRoles().map(role => {
      if (role === 'admin') {
        this.permission = true;
      }
    });
    this.form =  new FormGroup({
      name: new FormControl(this.data.name, Validators.required),
      description: new FormControl(this.data.description, Validators.required),
      minimalDuration: new FormControl(this.data.minimalDuration, [Validators.required, Validators.min(1)]),
      completeTime: new FormControl(this.data.completeTime, [ Validators.required, Validators.min(1)]),
      questionNums: new FormControl(this.data.questionNums, [Validators.required, Validators.min(1)]),
      triesCount: new FormControl(this.data.triesCount, [Validators.required, Validators.min(1)]),
      price: new FormControl(this.data.price, [Validators.required, Validators.min(1)])
    });
    if (!this.permission) {
      this.form.get('price').disable();
    }
  }

  onNoClick(): void {
    this.dialogRef.close(false);
  }

  editProgram(): void{
    this.restService.put(environment.adminUrl, 'manage/program', {
      name: this.form.get('name').value,
      description: this.form.get('description').value,
      minimalDuration: this.form.get('minimalDuration').value,
      completeTime: this.form.get('completeTime').value,
      questionNums: this.form.get('questionNums').value,
      triesCount: this.form.get('triesCount').value,
      price: this.form.get('price').value,
      studyProgramId: this.data.studyProgramId,
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
