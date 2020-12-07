import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {NavigationComponent} from './navigation/navigation.component';
import {RouterModule} from '@angular/router';
import { FooterComponent } from './footer/footer.component';
import {AuthGuard} from './guards/auth.guard';
import {AuthService} from './auth.service';
import {appInterceptorProvider} from './app.interceptor';


@NgModule({
  declarations: [
    NavigationComponent,
    FooterComponent
  ],
  imports: [
    CommonModule,
    RouterModule
  ],
  providers: [
    AuthGuard,
    appInterceptorProvider,
    AuthService
  ],
  exports: [
    NavigationComponent,
    FooterComponent
  ]
})
export class CoreModule {
}
