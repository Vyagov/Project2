import {Component} from '@angular/core';
import { AuthService } from './core/auth.service';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'FrontEnd-Angular';
  isReady$ = this.authService.isReady$;

  constructor(private authService: AuthService, private http: HttpClient) {

  }
}
