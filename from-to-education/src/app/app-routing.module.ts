import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {ShowPageComponent} from './pages/show-page/show-page.component';
import {EducationComponent} from './pages/user-lk/education/education.component';
import {TestComponent} from './pages/user-lk/test/test.component';
import {ResultComponent} from './pages/user-lk/result/result.component';
import {LiteratureComponent} from './pages/user-lk/literature/literature.component';
import {StatisticsComponent} from './pages/administration/admin-page/statistics/statistics.component';
import {FinalProgramComponent} from './pages/user-lk/final-program/final-program.component';
import {FinalAdminTestComponent} from './pages/final-admin-test/final-admin-test.component';
import {AuthGuard} from './services/guard/auth.guard';
import {AdminPageComponent} from './pages/administration/admin-page/admin-page.component';
import {AddProgramComponent} from './pages/administration/admin-page/dialogs/add-program/add-program.component';
import {QuestionComponent} from './pages/administration/admin-page/question/question.component';
import {AddLiteratureComponent} from './pages/administration/admin-page/add-literature/add-literature.component';

const routes: Routes = [
  { path: '',
    redirectTo: '/show',
    pathMatch: 'full',
  },
  {
    path: 'show',
    component: ShowPageComponent
  },
  {
    path: 'final',
    component: FinalProgramComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'education',
    component: EducationComponent,
  },
  {
    path: 'administration',
    component: AdminPageComponent,
    canActivate: [AuthGuard],
    data: { roles: ['admin', 'razrab'] }
  },
  {
    path: 'test/:id/:type',
    component: TestComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'result/:id',
    component: ResultComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'literature/:id',
    component: LiteratureComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'admintest/:id',
    component: FinalAdminTestComponent
  }
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
