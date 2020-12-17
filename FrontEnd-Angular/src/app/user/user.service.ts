import {Injectable} from '@angular/core';
import {Observable, of} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {AuthService} from '../core/auth.service';

@Injectable()
export class UserService {

  constructor(private http: HttpClient, private authService: AuthService) {
  }

  createAccount(date: any): Observable<any> {
    return this.http.post('/users/register', date);
  }

  // getCurrentUserProfile(): Observable<any> {
  //   return this.http.get(`/users/profile`).pipe(
  //     tap((user: User) => this.authService.updateCurrentUser(user))
  //   );
  // }
  //
  // updateProfile(data: any): Observable<User> {
  //   return this.http.put(`/users/profile`, data).pipe(
  //     tap((user: User) => this.authService.updateCurrentUser(user))
  //   );
  // }
}
