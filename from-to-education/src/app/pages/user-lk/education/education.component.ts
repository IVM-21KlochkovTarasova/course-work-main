import {AfterViewInit, Component, OnInit} from '@angular/core';
import {RestService} from '../../../services/rest.service';
import {Program} from '../../../classes/Program';
import {ActivatedRoute, Router} from '@angular/router';
import {InfoDialogComponent} from '../../../common/info-dialog/info-dialog.component';
import {MatDialog} from '@angular/material/dialog';
import {environment} from "../../../../environments/environment";

@Component({
  selector: 'app-education',
  templateUrl: './education.component.html',
  styleUrls: ['./education.component.css']
})
export class EducationComponent implements OnInit {

  eduPrograms = [];
  stat =  'false';
  buttons = [
    {
      name: 'Пробное тестирование',
      action: (id: number) => {
        this.test(id);
      }
    },
    {
      name: 'Итоговое тестирование',
      action: (id: number) => {
        this.finish(id);
      }
    },
    {
      name: 'Литература',
      action: (id: number) => {
        this.getLiterature(id);
      }
    }
  ];

  constructor(private restService: RestService,
              public dialog: MatDialog,
              private router: Router) { }

  ngOnInit(): void {
    this.restService.get(environment.lkUrl, 'study/user_programs', {
    }).subscribe(
      result => {
        console.log(result);
        this.eduPrograms = result;
        this.eduPrograms.map(v => {
          v.panelOpenState = false;
          if (v.contract.isComplete === true) {
            v.color = '#2ca632';
          } else {
            v.color = '#5ecdb4';
          }
        });
      }, error => {
      }
    );
  }

  goProgram(id) {
    this.router.navigate(['program/:' + id]);
  }

  test(id: number) {
    console.log('test');
    this.router.navigate(['test/' + id + '/False']);
  }

  finish(id: number) {
    const dialogRef = this.dialog.open(InfoDialogComponent, {
      width: '380px',
      data: 'Вы действиельно хотите пройти итоговый тест?',
      autoFocus: false,
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      if (result) {
        this.router.navigate(['test/' + id + '/True']);
      }
    });
  }

  getLiterature(id: number): void {
    console.log(id);
    this.router.navigate(['literature/' + id]);
  }

  getResult(id: number) {
    this.router.navigate(['final']);
  }
}
