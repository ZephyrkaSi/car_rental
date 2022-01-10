import {userDetails} from "../user-details/user-details";

export interface user {
  id: number;
  username: string;
  password: string;
  email: string;
  role: string;
  enabled: boolean;
  lastLogin: string;
  userDetails: userDetails;
}
