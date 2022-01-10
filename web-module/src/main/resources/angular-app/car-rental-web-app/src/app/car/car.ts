import {carModel} from "../car-model/car-model";

export interface car {
  id: number;
  stateNumber: string;
  carModel: carModel;
  carStatus: string;
}
