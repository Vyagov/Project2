import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import {Observable} from 'rxjs/internal/Observable';
import {map, tap} from 'rxjs/operators';
import {User} from '../shared/interfaces';

@Injectable()
export class AuthService {

  // tslint:disable-next-line:variable-name
  private _currentUser: BehaviorSubject<User | null> = new BehaviorSubject(undefined);
  currentUser$ = this._currentUser.asObservable();
  isLogged$ = this.currentUser$.pipe(map(user => !!user));

  constructor(public http: HttpClient) {
  }

  authenticated = false;

  // tslint:disable-next-line:typedef
  authenticate(credentials, callback) {

    const headers = new HttpHeaders(credentials ? {
      authorization: 'Basic ' + btoa(credentials.username + ':' + credentials.password)
    } : {});

    this.http.get('/users/login', {headers})
      .subscribe((response) => {
        let data: any;
        data = response;
        const u = data.principal;
        this.authenticated = !!response;
        return callback && callback(data);
      });

  }

  public logIn(data: any): Observable<any> {
    console.log(data);
    const headers = new HttpHeaders();
    headers.set('Accept', 'application/json');
    const base64Credential: string = btoa(data.username + ':' + data.password);
    headers.set('Authorization', 'Basic ' + base64Credential);
    console.log(headers);

    return this.http.get('/users/login', {headers});
  }

  logout(): Observable<any> {
    return this.http.post(`/users/logout`, {}).pipe(
      tap((user: User) => this._currentUser.next(null))
    );
  }

}
