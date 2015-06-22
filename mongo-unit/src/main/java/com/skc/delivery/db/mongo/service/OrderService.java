package com.skc.delivery.db.mongo.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.skc.delivery.db.mongo.order.Delivery;
import com.skc.delivery.db.mongo.order.Order;

@Service("orderService")
public class OrderService {
	
	@Autowired
	MongoTemplate mongoTemplate;
	
	public void createOrder(Order order){
		MongoOperations mongoOperations = mongoTemplate;
		mongoOperations.save(order);
	}
	
	public List<Order> fetchOrder(String username){
		MongoOperations mongoOperations = mongoTemplate;
		return mongoOperations.find(new Query(Criteria.where("username").is(username)), Order.class);
	}
	
	public List<Order> fetchOrderForJob(){
		MongoOperations mongoOperations = mongoTemplate;
		return mongoOperations.find(new Query(Criteria.where("status").ne("DELIVERED")), Order.class);
	}
	
	public Order fetchOrderByOrderNumber(String orderNumber){
		MongoOperations mongoOperations = mongoTemplate;
		return mongoOperations.findOne(new Query(Criteria.where("orderNumber").is(orderNumber)), Order.class);
	}
	
	public List<Order> fetchOrderByStatus(String status){
		MongoOperations mongoOperations = mongoTemplate;
		return mongoOperations.find(new Query(Criteria.where("status").is(status)), Order.class);
	}
	
	public void updateOrderStatus(String status,Order order){
		MongoOperations mongoOperations = mongoTemplate;
		Update update = new Update();
		update.set("status", status);
		mongoOperations.updateMulti(new Query(Criteria.where("orderNumber").is(order.getOrderName())), update, Order.class);
	}
	
	public void updateOrderDeliveryStatus(Order order,Delivery delivery,String status){
		MongoOperations mongoOperations = mongoTemplate;
		Update update = new Update();
		Set<Delivery> deliveries = order.getDelivery();
		deliveries.add(delivery);
		update.set("deliveries", deliveries);
		update.set("status", status);
		mongoOperations.updateMulti(new Query(Criteria.where("orderNumber").is(order.getOrderNumber())), update, Order.class);
	}
}
