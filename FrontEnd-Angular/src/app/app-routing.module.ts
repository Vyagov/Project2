import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './home/home.component';
import {NotFoundComponent} from './not-found/not-found.component';
// import {AuthGuard} from './core/guards/auth.guard';

const routes: Routes = [
  {
    path: '',
    // canActivateChild: [AuthGuard],
    children: [
      {
        path: '',
        pathMatch: 'full',
        redirectTo: '/home'
      },
      {
        path: 'home',
        component: HomeComponent,
        data: {
          title: 'HOME'
        }
      },
      {
        path: 'users',
        // canActivateChild: [AuthGuard],
        loadChildren: () => import('./user/user.module').then(m => m.UserModule)
      },
      {
        path: '**',
        component: NotFoundComponent,
        data: {
          title: '404'
        }
      }
    ]
  }
];

export const AppRoutingModule = RouterModule.forRoot(routes);

