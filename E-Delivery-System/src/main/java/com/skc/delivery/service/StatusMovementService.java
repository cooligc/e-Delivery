package com.skc.delivery.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.skc.delivery.db.mongo.order.Delivery;
import com.skc.delivery.db.mongo.order.Order;
import com.skc.delivery.db.mongo.service.OrderService;
import com.skc.delivery.status.OrderStatusMapping;
import com.skc.delivery.util.ApplicationUtils;

@Service
public class StatusMovementService {
	
	@Resource(name="orderService")
	OrderService orderService;
	
	@Scheduled(fixedRate=60000)
	public void changeStatus(){
		List<Order> orders = orderService.fetchOrderForJob();
		
		for (Order order : orders) {
			int status = OrderStatusMapping.getCurrentStatus(order.getStatus());
			if(status==6){
				continue;
			}
			
			String nextStatus = OrderStatusMapping.getStatus(++status);
			if(nextStatus.equalsIgnoreCase("IN_VALID")){
				continue;
			}
			Delivery delivery = OrderStatusMapping.getDelivery(status);
			if(status==6){
				delivery.setLocation(order.getAddress());
			}
			delivery.setDeliveryTime(ApplicationUtils.getCurrentTimeStamp());
			orderService.updateOrderDeliveryStatus(order, delivery, nextStatus);
		}
		
	}
}
