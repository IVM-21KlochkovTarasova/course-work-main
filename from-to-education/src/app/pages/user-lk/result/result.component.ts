import { Component, OnInit } from '@angular/core';
import {RestService} from '../../../services/rest.service';
import {ActivatedRoute, Router} from '@angular/router';
import {MatDialog} from '@angular/material/dialog';
import {environment} from "../../../../environments/environment";

@Component({
  selector: 'app-result',
  templateUrl: './result.component.html',
  styleUrls: ['./result.component.css']
})
export class ResultComponent implements OnInit {

  result;
  status = false;
  constructor(private restService: RestService,
              private activatedRoute: ActivatedRoute,
              public dialog: MatDialog,
              private router: Router) { }

  ngOnInit(): void {
    this.restService.get(environment.lkUrl, 'study/exam/result', {
      testId: this.activatedRoute.snapshot.params.id
    }).subscribe(
      res => {
        this.result = res;
        console.log(this.result);
      }, error => {
      }
    );
  }


  clickEvent(){
    this.status = !this.status;
  }
}
