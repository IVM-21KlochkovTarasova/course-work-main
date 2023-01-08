import {AfterViewInit, Component, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {RestService} from '../../services/rest.service';
import {MatDialog} from '@angular/material/dialog';
import {OkInformComponent} from '../../common/ok-inform/ok-inform.component';
import {Router} from '@angular/router';
import {AuthService} from '../../services/auth.service';
import {environment} from "../../../environments/environment";

@Component({
  selector: 'app-show-page',
  templateUrl: './show-page.component.html',
  styleUrls: ['./show-page.component.css']
})
export class ShowPageComponent implements OnInit {

  @Output() program: any = [];
  choice: string;
  section: any = [];
  nameProgram: string;
  programs: any = [];
  currentSec: any;
  currentCourse: any;
  constructor(public dialog: MatDialog,
              public authService: AuthService,
              public restService: RestService) { }

  ngOnInit(): void {
    this.loadSection();
  }

  // загрузка программ
  loadProgram(item: any): void{
    this.restService.get(environment.lkUrl, 'study/programs', {
      courseId: item.courseId
    }).subscribe(
      result => {
        this.program = result;
        this.choice = 'program';
        this.currentCourse = {name: item.name, courseId: item.courseId};
      }, err => {}
    );
  }

  clickProgram(): void {
    this.program = this.programs;
    this.choice = 'program';
  }

  // загрузка всех направлений определенного раздела
  loadCourses(item: any): void {
    this.restService.get(environment.lkUrl, 'study/courses', {
      studySectionId: item.studySectionId
    }).subscribe(result => {
        this.program = result.list;
        this.choice = 'courses';
        this.currentSec =
          {
            name: item.name,
            studySectionId: item.studySectionId
          };
      }, err => {
      }
    );
  }

  buy(item): void{
    if (this.authService.getLoggedUser() === undefined) {
      this.authService.login();
    } else {
      this.restService.post(environment.orderUrl, 'contract', {
        studyProgramId: item.studyProgramId
      }).subscribe(
        () => {this.showAfterBought('Поздравляю с покупкой')},
        err => {this.showAfterBought('Ошибка при покупке (Возможно покупка уже была совершена)')}
      );
    }
  }

  showAfterBought(text: string){
    const dialogRef1 = this.dialog.open(OkInformComponent, {
      width: '380px',
      data: text
    });
    dialogRef1.afterClosed().subscribe(() => {
      this.loadProgram(this.currentCourse);
    });
  }

  loadSection(): void {
    this.restService.get(environment.lkUrl, 'study/section').subscribe(
      result => {
        this.program = result.list;
        this.choice = 'section';
      },
      err => {}
    );
  }

}
