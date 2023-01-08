import {Component, Input, OnInit} from '@angular/core';
import {RestService} from '../../../services/rest.service';
import {MatDialog} from '@angular/material/dialog';
import {ActivatedRoute, Router} from '@angular/router';
import {DomSanitizer} from '@angular/platform-browser';
import {environment} from '../../../../environments/environment';

@Component({
  selector: 'app-literature',
  templateUrl: './literature.component.html',
  styleUrls: ['./literature.component.css']
})
export class LiteratureComponent implements OnInit {

  literatures: any;
  status = false;
  displayURL;
  textSearch: string = "";
  constructor(private restService: RestService,
              public dialog: MatDialog,
              private activatedRoute: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.loadLiterature();
  }

  loadLiterature(): void{
    this.restService.get(environment.lkUrl,'study/literature', {
      studyProgramId: this.activatedRoute.snapshot.params.id,
      filter: this.textSearch
    }).subscribe(
      result => {
        this.literatures = result;
        this.literatures.map( l => {
          l.status = false;
        });
      }, error => {
      }
    );
  }

  search(): void{
    this.loadLiterature();
  }

  openLink(link: string): void {
    window.open(link);
  }

  clickEvent(lit): void{
    lit.status = !lit.status;
  }

}
