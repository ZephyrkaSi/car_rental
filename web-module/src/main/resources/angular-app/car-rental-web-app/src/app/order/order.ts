import {user} from "../user/user";
import {car} from "../car/car";
import {carModel} from "../car-model/car-model";
import {orderStatus} from "../order-status/order-status";

export interface order {
  id: number;
  orderDate: string;
  user: user;
  dateAndTimeFrom: string;
  dateAndTimeTo: string;
  car: car;
  carModel: carModel;
  priceWithDiscount: number;
  discount: number;
  declineReason: string;
  orderStatus: orderStatus;
}
