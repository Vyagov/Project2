import { IBase } from './base';

export interface IUser extends IBase {
  watchlist: string[];
  firstName: string;
  lastName: string;
  username: string;
  password: string;
  email: string;
}
