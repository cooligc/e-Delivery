package com.skc.delivery.status;

import com.skc.delivery.db.mongo.order.Delivery;


public class DeliveryBuilder {
	Delivery delivery =null;
	
	public DeliveryBuilder createDelivery(){
		delivery= new Delivery();
		return this;
	}
	
	public DeliveryBuilder addStatus(String status){
		delivery.setStatus(status);
		return this;
	}
	
	public DeliveryBuilder addDeliveryTime(String time){
		delivery.setDeliveryTime(time);
		return this;
	}
	 
	public DeliveryBuilder addDeliveryDescription(String description){
		delivery.setDescription(description);
		return this;
	}
	
	public DeliveryBuilder addLocation(String location){
		delivery.setLocation(location);
		return this;
	}
	
	public Delivery build(){
		return delivery;
	}
	
}
