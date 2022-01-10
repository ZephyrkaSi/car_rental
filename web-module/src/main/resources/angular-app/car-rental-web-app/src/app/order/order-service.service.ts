import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {order} from "./order";

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private url = 'http://localhost:8080/orders/api/all';

  constructor(private http: HttpClient) {
  }

  getOrders() {
    return this.http.get<order[]>(`${this.url}`);
  }
}
