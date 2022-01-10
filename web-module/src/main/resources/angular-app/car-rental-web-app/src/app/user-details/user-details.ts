import {discountStatus} from "../discount-status/discount-status";

export interface userDetails {
  id: number;
  firstName: string;
  lastName: string;
  passportData: string;
  totalRentalTime: number;
  status: discountStatus;
}
