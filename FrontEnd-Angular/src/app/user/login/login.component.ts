import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {AuthService} from '../../core/auth.service';
import {Router} from '@angular/router';
import {User} from '../../shared/interfaces';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class LoginComponent implements OnInit {
  user: User = new User();
  errorMessage: string;

  constructor(private authService: AuthService, private router: Router) {
  }

  ngOnInit(): void {
  }

  login(): void {
    this.authService.authenticate(this.user, (e) => {
      this.router.navigateByUrl('/home');
      console.log(e);
      let resp: any;
      resp = e.principal;
      // this.user.fullName = 'ndh';
      if (resp) {
        // store user details  in local storage to keep user logged in between page refreshes
        localStorage.setItem('currentUser', JSON.stringify(resp));
      }
    });
  }
}
