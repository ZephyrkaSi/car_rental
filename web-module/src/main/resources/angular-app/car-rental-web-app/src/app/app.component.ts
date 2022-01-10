import {Component} from '@angular/core';
import {OrderService} from './order/order-service.service';
import {Router} from "@angular/router";
import {order} from "./order/order";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.less']
})
export class AppComponent {
  title = 'Orders';
  orders: order[] | undefined;
  id: any;
  key: string = 'id';
  reverse: boolean = false;

  //dataSource: OrderTableDataSource;

  constructor(private router: Router, private orderService: OrderService) {
    /*
        this.dataSource = new OrderTableDataSource();
    */
  }

  getOrders() {
    this.orderService.getOrders().subscribe(data => {
      this.orders = data;
    });
  }
}
