import { Component, OnInit } from '@angular/core';
import {AuthService} from '../core/auth.service';
import {NavigationEnd, Router} from '@angular/router';
import {filter} from 'rxjs/operators';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {

  isLogged$ = this.authService.isLogged$;

  url: string;

  constructor(
    private authService: AuthService,
    router: Router
  ) {

    router.events.pipe(filter(e => e instanceof NavigationEnd)).subscribe((e: NavigationEnd) => {
      this.url = e.url;
    });
  }
}
