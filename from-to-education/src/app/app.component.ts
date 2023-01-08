import {Component, OnInit} from '@angular/core';
import {Menu} from './classes/Menu';
import {MatDialog} from '@angular/material/dialog';
import {RestService} from './services/rest.service';
import {environment} from '../environments/environment';
import {AuthGuard} from './services/guard/auth.guard';
import {AuthService} from './services/auth.service';
import {KeycloakService} from 'keycloak-angular';
import {Router} from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent  implements OnInit {
  title = 'from-to-education';
  userName: string;
  public menuList: Menu[] = [];

  constructor(public dialog: MatDialog,
              private restService: RestService,
              public authService: AuthService,
              public keycloakService: KeycloakService,
              private router: Router
              ) {
  }

  ngOnInit(): void {
    // выгрузка username
    const userInfo = this.keycloakService.getKeycloakInstance().loadUserInfo();
    console.log(userInfo);
    // @ts-ignore
    userInfo.then( res => this.userName = res.preferred_username);
  }

  login(): void {
    this.authService.login();
  }

  logout(): void {
   this.authService.logout();
  }

  getLk(): void {
    console.log('go lk');
    const link = this.authService.getRoles().filter(role => {
      let result = false;

      console.log(role);
      if ((role === 'admin') || (role === 'razrab')) {
        console.log(role);
        this.router.navigate(['/administration']);
        result = true;
      }
      return result;
    });
    if (link.length === 0) {
     this.router.navigate(['/education']);
   }
  }
}

