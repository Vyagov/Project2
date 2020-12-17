import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {AuthService} from '../../core/auth.service';
import {Router} from '@angular/router';
import {rePasswordValidatorFactory} from '../../shared/validators';
import {User} from '../../shared/interfaces';
import {UserService} from '../user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class RegisterComponent implements OnInit {

  user: User = new User();
  errorMessage: string;

  constructor(public userService: UserService, public router: Router) {
  }

  ngOnInit(): void {
  }

  register(): void {
    this.userService.createAccount(this.user)
      .subscribe(data => {
          this.router.navigate(['users/login']);
        }, err => {
          console.log(err);
          this.errorMessage = 'username already exist';
        }
      );
  }
}
