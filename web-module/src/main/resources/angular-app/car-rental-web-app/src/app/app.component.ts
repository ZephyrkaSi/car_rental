import {Component, OnInit} from '@angular/core';
import {OrderService} from './order/order-service.service';
import {Router} from "@angular/router";
import {Order} from "./order/order";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.less']
})
export class AppComponent implements OnInit {
  title = 'Orders';
  orders: Order[] | undefined;

  constructor(private router: Router, private orderService: OrderService) {
  }

  getOrders() {
    this.orderService.getOrders().subscribe(data => {
      this.orders = data;
    });
  }

  ngOnInit(): void {
    this.router.events.subscribe(value => {
      this.getOrders();
    });
  }
}
