import {Component, Inject, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';
import {RestService} from '../../../../../services/rest.service';
import {OkInformComponent} from '../../../../../common/ok-inform/ok-inform.component';
import {environment} from "../../../../../../environments/environment";

@Component({
  selector: 'app-edit-section',
  templateUrl: './edit-section.component.html',
  styleUrls: ['./edit-section.component.css']
})
export class EditSectionComponent implements OnInit {

  form: FormGroup;
  err = false;
  errText: string;

  constructor(public dialogRef: MatDialogRef<EditSectionComponent>,
              public restServise: RestService,
              public dialog: MatDialog,
              @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit(): void {
    console.log(this.data);
    this.form =  new FormGroup({
      "name": new FormControl(this.data.item.name, Validators.required),
      "description": new FormControl(this.data.item.description, Validators.required)
    });
  }

  onNoClick(): void {
    this.dialogRef.close(false);
  }

  editSec() {
    this.restServise.put(environment.adminUrl, 'manage/section', {
      "name": this.form.get('name').value,
      "description": this.form.get('description').value,
      "studySectionId": this.data.item.studySectionId
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

  editCourse() {
    this.restServise.put(environment.adminUrl, 'manage/course', {
      "name": this.form.get('name').value,
      "description": this.form.get('description').value,
      "courseId": this.data.item.courseId
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
