import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription, timer} from 'rxjs';
import {RestService} from '../../services/rest.service';
import {ActivatedRoute, Router} from '@angular/router';
import {MatDialog} from '@angular/material/dialog';
import {DialogComponent} from '../user-lk/test/dialog/dialog.component';
import {InfoDialogComponent} from '../../common/info-dialog/info-dialog.component';
import {environment} from '../../../environments/environment';

@Component({
  selector: 'app-final-admin-test',
  templateUrl: './final-admin-test.component.html',
  styleUrls: ['./final-admin-test.component.css']
})
export class FinalAdminTestComponent implements OnInit, OnDestroy {

  question;
  answers;
  next = true;
  testId: number;
  tryAnswerId: number;
  countDown: Subscription;
  counter = 1200;
  // counter = 5;
  tick = 1000;
  progress: any;
  status = false;
  path: string;
  constructor(private restService: RestService,
              private activatedRoute: ActivatedRoute,
              public dialog: MatDialog,
              private router: Router) { }
  ngOnInit(): void {
    this.restService.get(environment.lkUrl, 'study/exam/question', {
      "program_id": this.activatedRoute.snapshot.params.id,
      "offset": 0
    }).subscribe(
      res => {
        this.progress = (100 / res.test.amount) * res.test.curr ;
        this.question = res.test;
        this.tryAnswerId = res.test.try_answer_id;
      }, error => {
      }
    );
    this.countDown = timer(0, this.tick).subscribe(() => {
      --this.counter;
      if (this.counter === 0) {
        this.countDown.unsubscribe();
        const dialogRef = this.dialog.open(DialogComponent, {
        });

        dialogRef.afterClosed().subscribe(resu => {
          this.router.navigate(['education']);
        });
      }
    });
  }
  ngOnDestroy(){
    this.countDown = null;
  }

  onClick(answer: any) {
    this.next = false;
    this.progress = (100 / this.question.amount) * (this.question.curr + 1) ;
    this.question.answers.filter(ans => {
      ans.select = ans === answer;
    });
  }

  clickEvent(){
    this.status = !this.status;
  }

  nextAnswer(): void{
    this.restService.get(environment.lkUrl, 'study/exam/question', {
      "program_id": this.activatedRoute.snapshot.params.id,
      "offset": this.question.curr + 1
    }).subscribe(
      res => {
        this.next = true;
        this.progress = (100 / res.test.amount) * res.test.curr ;
        this.question = res.test;
        this.tryAnswerId = res.test.try_answer_id;
      }, error => {
      }
    );
  }

  exit() {
    const dialogRef = this.dialog.open(InfoDialogComponent, {
      width: '380px',
      data: 'Вы действиельно хотите выйти?',
      autoFocus: false,
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      if (result) {
        this.router.navigate(["/education"]);
      }
    });
  }

}
