import {Component, OnDestroy} from '@angular/core';
import {AuthService} from '../auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent implements OnDestroy {
  isLogged$ = this.authService.isLogged$;

  constructor(
    private authService: AuthService,
    private router: Router) {
  }

  logoutHandler(): void {
    this.authService.logout().subscribe(() => this.router.navigate(['/users/login']));
  }

  ngOnDestroy(): void {
  }
}
