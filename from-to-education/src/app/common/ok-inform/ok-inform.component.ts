import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';

@Component({
  selector: 'app-ok-inform',
  templateUrl: './ok-inform.component.html',
  styleUrls: ['./ok-inform.component.css']
})
export class OkInformComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<OkInformComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any) { }

  ngOnInit(): void {
  }

}
