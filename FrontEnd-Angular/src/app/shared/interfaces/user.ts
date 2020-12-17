import { Base } from './base';

export class User extends Base {
  watchlist: string[];
  firstName: string;
  lastName: string;
  username: string;
  password: string;
  email: string;
  createDate: string;
}
