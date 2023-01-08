import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';
import {RestService} from '../../../../../services/rest.service';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {OkInformComponent} from '../../../../../common/ok-inform/ok-inform.component';
import {environment} from "../../../../../../environments/environment";

@Component({
  selector: 'app-dialog-liter',
  templateUrl: './dialog-liter.component.html',
  styleUrls: ['./dialog-liter.component.css']
})
export class DialogLiterComponent implements OnInit {

  form: FormGroup;
  err = false;
  errText: string;
  fileToUpload: File = null;

  constructor(public dialogRef: MatDialogRef<DialogLiterComponent>,
              public restService: RestService,
              public dialog: MatDialog,
              @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit(): void {
    this.form =  new FormGroup({
      title: new FormControl(null, Validators.required),
      description: new FormControl(null),
      link: new FormControl(null)
    });
  }

  onNoClick(): void {
    this.dialogRef.close(false);
  }

  addLiter(): void{
    this.restService.post(environment.adminUrl, 'manage/literature',  {
      studyProgramId: this.data,
      title: this.form.get('title').value,
      description: this.form.get('description').value,
      link: this.form.get('link').value
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

  handleFileInput(file): void {
    this.fileToUpload = file.target.files[0];
  }
}
