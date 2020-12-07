import {Component, OnInit} from '@angular/core';
import {AuthService} from '../auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnInit {
  hideNavigation = false;

  isLogged$ = this.authService.isLogged$;
  isReady$ = this.authService.isReady$;

  constructor(private authService: AuthService, private router: Router) {
  }

  logoutHandler(): void {
    this.authService.logout().subscribe(() => this.router.navigate(['/user/login']));
  }

  ngOnInit(): void {
  }

}
