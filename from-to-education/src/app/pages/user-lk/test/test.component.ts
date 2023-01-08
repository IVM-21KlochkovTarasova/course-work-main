import {Component, OnDestroy, OnInit, Pipe, PipeTransform} from '@angular/core';
import {RestService} from '../../../services/rest.service';
import {ActivatedRoute, Router} from '@angular/router';
import {Subscription, timer} from 'rxjs';
import {MatDialog} from '@angular/material/dialog';
import {DialogComponent} from './dialog/dialog.component';
import {InfoDialogComponent} from '../../../common/info-dialog/info-dialog.component';
import {error} from 'protractor';
import {OkInformComponent} from '../../../common/ok-inform/ok-inform.component';
import {environment} from '../../../../environments/environment';

@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrls: ['./test.component.css']
})
export class TestComponent implements OnInit, OnDestroy {

  question;
  next = true;
  testId: number;
  tryAnswerId: number;
  countDown: Subscription;
  counter: number;
 // counter = 5;
  tick = 1000;
  progress: any;
  path: string;

  constructor(private restService: RestService,
              private activatedRoute: ActivatedRoute,
              public dialog: MatDialog,
              private router: Router) { }

  ngOnInit(): void {
    console.log(this.activatedRoute.snapshot.params.id);
    this.restService.post(environment.lkUrl, 'study/exam', {
      programId: this.activatedRoute.snapshot.params.id,
      isFinal: this.activatedRoute.snapshot.params.type
    }).subscribe(
      result => {
        if (result.type === 'error') {
          this.errorMsg(result);
        }
        this.testId = result.testId;
        this.counter = result.testTime * 60;
        this.getQuestion();
      }, err => {
        console.log(err);
        this.tick = 0;
        this.router.navigate(['education']);
        const dialogRef = this.dialog.open(OkInformComponent, {
          width: '380px',
          data: err.message,
          autoFocus: false,
        });

        dialogRef.afterClosed().subscribe(result => {
          console.log('The dialog was closed');
        });
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
  ngOnDestroy(): void{
    this.countDown = null;
  }

  errorMsg(err: any): void{
    this.tick = 0;
    this.router.navigate(['education']);
    const dialogRef = this.dialog.open(OkInformComponent, {
      width: '380px',
      data: err.message,
      autoFocus: false,
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
    });
  }

  onClick(answer: any): void {
    this.next = false;
    this.question.answer.filter(ans => {
      ans.select = ans === answer;
    });
  }

  exit(): void {
    const dialogRef = this.dialog.open(InfoDialogComponent, {
      width: '380px',
      data: 'Вы действиельно хотите выйти?',
      autoFocus: false,
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      if (result) {
        this.router.navigate(['/education']);
      }
    });
  }

  //получение вопросов
  getQuestion(): void {
    this.restService.get(environment.lkUrl, 'study/exam/question', {
      testId: this.testId
    }).subscribe(
      res => {
        if ( res.test !== null) {
          this.progress = (100 / res.test.amount) * res.test.curr;
          this.question = res.test;
          this.tryAnswerId = res.test.tryAnswerId;
          this.question.answer.filter(ans => {
            ans.select = false;
          });
        } else if (res.isComplete === true){
          this.getResult();
        }
      }, error => {
      }
    );
  }

  //если отвечаем на последний вопрос
  getResult(): void {
    this.question.answer.map(ans => {
      if (ans.select === true) {
        this.restService.get(environment.lkUrl, 'study/exam/result', {
          testId: this.testId
        }).subscribe(
          result => {
            this.router.navigate(['result/' + this.testId]);
          });
      }
    });
  }

  //отправляем ответы
  putAnswer(): void{
    this.next = true;
    this.question.answer.map(ans => {
      if (ans.select === true) {
        this.restService.post(environment.lkUrl, 'study/exam/answer', {
          answerId: ans.answerId,
          tryAnswerId: this.tryAnswerId
        }).subscribe(() => this.getQuestion());
      }
    });
  }

}

@Pipe({
  name: 'formatTime'
})
export class FormatTimePipe implements PipeTransform {
  transform(value: number): string {
    const minutes: number = Math.floor(value / 60);
    return (
      ('00' + minutes).slice(-2) +
      ':' +
      ('00' + Math.floor(value - minutes * 60)).slice(-2)
    );
  }
}
