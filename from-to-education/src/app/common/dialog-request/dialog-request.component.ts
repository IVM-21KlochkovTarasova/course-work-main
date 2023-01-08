import { Component, OnInit } from '@angular/core';
import {MatDialog, MatDialogRef} from '@angular/material/dialog';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {RestService} from '../../services/rest.service';
import {environment} from "../../../environments/environment";

@Component({
  selector: 'app-dialog-request',
  templateUrl: './dialog-request.component.html',
  styleUrls: ['./dialog-request.component.css']
})
export class DialogRequestComponent implements OnInit {

  form: FormGroup;
  selectedFile: File = undefined;
  file: File = null; //
  constructor(public dialog: MatDialog,
              public dialogRef: MatDialogRef<DialogRequestComponent>,
              private restService: RestService) { }

  ngOnInit(): void {
    this.form =  new FormGroup({
      "name": new FormControl(null, Validators.required),
      "organization": new FormControl(null, Validators.required),
      "phone": new FormControl(null, Validators.required),
      "email": new FormControl(null, [Validators.required, Validators.email]),
      "request_text": new FormControl(''),
      "file": new FormControl(null)
    });
  }

  request(): void {
    this.restService.postFile(environment.orderUrl, 'put_order', {
      'fio': this.form.get('name').value,
      'organization': this.form.get('organization').value,
      'email': this.form.get('email').value,
      'phone': this.form.get('phone').value,
      'request_text': this.form.get('request_text').value,
    }, this.selectedFile).subscribe(
      res => {
        console.log(res);
        this.dialogRef.close(true);
      }, error => {
      }
    );
  }


  csvInputChange(fileInputEvent: any) {
    console.log(fileInputEvent.target.files[0]);
    this.selectedFile = fileInputEvent.target.files[0];
  }

  onFileInput(event): void {
    console.log(event.target.files);
    if (event.target.files.length !== 0) {
      this.selectedFile = event.target.files[0];
    } else {
    }
  }
}
