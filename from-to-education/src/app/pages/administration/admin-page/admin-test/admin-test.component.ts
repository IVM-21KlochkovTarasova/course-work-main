import {Component, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {MatDialog} from '@angular/material/dialog';
import {RestService} from '../../../../services/rest.service';
import {ActivatedRoute, Router} from '@angular/router';
import {AddNewSectionComponent} from '../dialogs/add-new-section/add-new-section.component';
import {EditSectionComponent} from '../dialogs/edit-section/edit-section.component';
import {AddProgramComponent} from '../dialogs/add-program/add-program.component';
import {EditProgramComponent} from '../dialogs/edit-program/edit-program.component';
import {InfoDialogComponent} from '../../../../common/info-dialog/info-dialog.component';
import {OkInformComponent} from '../../../../common/ok-inform/ok-inform.component';
import {AuthService} from '../../../../services/auth.service';
import {environment} from "../../../../../environments/environment";

@Component({
  selector: 'app-admin-test',
  templateUrl: './admin-test.component.html',
  styleUrls: ['./admin-test.component.css']
})
export class AdminTestComponent implements OnInit {


  firstFormGroup: FormGroup;
  @Output() program: any = [];
  choice: string;
  section: any = [];
  nameProgram: string;
  programs: any = [];
  currentSec: any;
  currentCourse: any;
  currentQuestion: any;
  currentLit: any;
  permission = false;
  constructor(private formBuilder: FormBuilder,
              public dialog: MatDialog,
              private router: Router,
              private authService: AuthService,
              public restService: RestService,
              private activateRoute: ActivatedRoute) { }

  ngOnInit(): void {
   const course = this.activateRoute.snapshot.params.prog;
   console.log(course);
   if (course !== course) {
   }
   this.authService.getRoles().map(role => {
      if (role === 'admin') {
        this.permission = true;
      }
    });
   this.loadSection();
  }

  loadProgram(item: any): void{
    this.restService.get(environment.lkUrl, 'study/programs', {courseId: item.courseId}).subscribe(
      result => {
        this.program = result;
        this.choice = 'program';
        this.currentCourse =
          {
            name: item.name,
            courseId: item.courseId
          };
      }, err => {
      }
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

  // загрузка всех направлений
  loadSection(): void {
    this.restService.get(environment.lkUrl, 'study/section').subscribe(
      result => {
        this.program = result.list;
        this.choice = 'section';
      }, err => {
      }
    );
  }

  test(program: any): void {
    this.router.navigate(['admintest/' + program.id]);
  }

  question(item: any): void {
    this.currentQuestion =
      {
        name: item.name,
        id: item.studyProgramId
      };
    this.choice = 'question';
  }

  literature(item: any): void {
    this.currentLit =
      {
        name: item.name,
        id: item.studyProgramId
      };
    this.choice = 'liter';
    // this.router.navigate(['add-liter/' + id]);
  }

  // добавление нового раздела/курса
  addNewSec(razdel: string): void{
    let data;
    if (razdel === 'section') {
      data = {
        sec: razdel
      };
    } else {
      data = {
        sec: razdel,
        id: this.currentSec.studySectionId
      };
    }
    const dialogRef = this.dialog.open(AddNewSectionComponent, {
      width: '460px',
      autoFocus: false,
      data
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');

      if ((razdel === 'section') && result) {
        console.log('sec');
        this.loadSection();
      } else  if ((razdel === 'courses') && result) {
        console.log(razdel);
        this.loadCourses(this.currentSec);
      }
    });
  }

  deleteSec(item: any): void{
    const dialogRef = this.dialog.open(InfoDialogComponent, {
      width: '380px',
      data: 'Вы действиельно хотите удалить раздел?',
      autoFocus: false,
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      if (result) {
        this.restService.delete(environment.adminUrl, 'manage/studySection',
          {studySectionId: item.studySectionId}).subscribe(res => {
          console.log(res.type);
          if (res.type === 'ok') {
            const dialogRef1 = this.dialog.open(OkInformComponent, {
              width: '380px',
              data: res.message
            });
            dialogRef1.afterClosed().subscribe(res1 => {
              console.log('The dialog was closed');
            });
            this.loadSection();
          }
          }, err => {
          }
        );
      }
    });
  }

  // редактирование направления/курса
  editSec(item: any, razdel: string): void{
    let data;
    if (razdel === 'section') {
      data = {
        sec: razdel,
        item
      };
    } else {
      data = {
        sec: razdel,
        id: this.currentSec.studySectionId,
        item
      };
    }
    const dialogRef = this.dialog.open(EditSectionComponent, {
      width: '460px',
      autoFocus: false,
      data
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      console.log(razdel);
      if ((razdel === 'section') && result) {
        this.loadSection();
      } else  if ((razdel === 'courses') && result) {
        this.loadCourses(this.currentSec);
      }
    });
  }

  deleteCourses(item: any): void{
    const dialogRef = this.dialog.open(InfoDialogComponent, {
      width: '380px',
      data: 'Вы действиельно хотите удалить направление?',
      autoFocus: false,
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      if (result) {
        this.restService.delete(environment.adminUrl, 'manage/course', {
          courseId: item.courseId
        }).subscribe(res => {
            if (res.type === 'ok') {
              console.log(res.type);
              if (res.type === 'ok') {
                const dialogRef1 = this.dialog.open(OkInformComponent, {
                  width: '380px',
                  data: res.message
                });
                dialogRef1.afterClosed().subscribe(res1 => {
                  console.log('The dialog was closed');
                });
                this.loadCourses(this.currentSec);
              }
            }
          }, err => {
          }
        );
      }
    });
  }

  deleteProgram(item: any): void{
    const dialogRef = this.dialog.open(InfoDialogComponent, {
      width: '380px',
      data: 'Вы действиельно хотите удалить программу?',
      autoFocus: false,
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      if (result) {
        this.restService.delete(environment.adminUrl, 'manage/program', {
          studyProgramId: item.studyProgramId
        }).subscribe(res => {
            if (res.type === 'ok') {
                  const dialogRef1 = this.dialog.open(OkInformComponent, {
                    width: '380px',
                    data: res.message
                  });
                  dialogRef1.afterClosed().subscribe(res1 => {
                    console.log('The dialog was closed');
                  });
                  this.loadProgram(this.currentCourse);
            }
          }, err => {
          }
        );
      }
    });
  }

  addProgram(): void{
    const dialogRef = this.dialog.open(AddProgramComponent, {
      width: '460px',
      autoFocus: false,
      data: this.currentCourse.courseId
    });

    dialogRef.afterClosed().subscribe(result => {
      this.loadProgram(this.currentCourse);
    });
  }

  editProgram(item: any): void {
    const dialogRef = this.dialog.open(EditProgramComponent, {
      width: '460px',
      autoFocus: false,
      data: item
    });

    dialogRef.afterClosed().subscribe(result => {
      this.loadProgram(this.currentCourse);
    });
  }


}
