package com.skc.delivery.status;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.skc.delivery.db.mongo.order.Delivery;
import com.skc.delivery.util.ApplicationUtils;

public class OrderStatusMapping {
	
	static final Map<String, Integer> MAP = new HashMap<String, Integer>();
	static final Map<Integer,Delivery> DELIVERY_MAP = new HashMap<Integer, Delivery>();
	
	static final Delivery DEFAULT_DELIVERY = new DeliveryBuilder()
																.createDelivery()
																.addDeliveryDescription("Product Delivery in Progress")
																.addLocation("Marathahalli")
																.addStatus(OrderStatus.IN_PROGRESS.getFriendlyName())
																.addDeliveryTime(ApplicationUtils.getCurrentTimeStamp())
																.build();
																
	
	static final Delivery PROVIDER_PACKED_DELIVERY = new DeliveryBuilder()
														.createDelivery()
														.addDeliveryDescription("Provider has packed the item")
														.addLocation("Marathahalli")
														.addStatus(OrderStatus.PROVIDER_PACKED.getFriendlyName())
														.addDeliveryTime(ApplicationUtils.getCurrentTimeStamp())
														.build();
	

	static final Delivery DELIVERY_STARTED_BY_PROVIDER= new DeliveryBuilder()
														.createDelivery()
														.addDeliveryDescription("Provider initiated the Delivery")
														.addLocation("Marathahalli")
														.addStatus(OrderStatus.DELIVERY_STARTED.getFriendlyName())
														.addDeliveryTime(ApplicationUtils.getCurrentTimeStamp())
														.build();

	static final Delivery IN_PROGRESS_DELIVERY = new DeliveryBuilder()
														.createDelivery()
														.addDeliveryDescription("Provider has initiated the Delivery and it is on the way")
														.addLocation("BTM Layout Depo")
														.addStatus(OrderStatus.DELIVERY_IN_PROGRESS.getFriendlyName())
														.addDeliveryTime(ApplicationUtils.getCurrentTimeStamp())
														.build();


	static final Delivery DELIVERED_PRODUCT = new DeliveryBuilder()
														.createDelivery()
														.addDeliveryDescription("Provider has packed the item")
														.addLocation("Marathahalli")
														.addStatus(OrderStatus.DELIVERED.getFriendlyName())
														.addDeliveryTime(ApplicationUtils.getCurrentTimeStamp())
														.build();
	static{
		MAP.put(OrderStatus.APPROVED.getFriendlyName(), 1);
		MAP.put(OrderStatus.IN_PROGRESS.getFriendlyName(), 2);
		MAP.put(OrderStatus.PROVIDER_PACKED.getFriendlyName(),3);
		MAP.put(OrderStatus.DELIVERY_STARTED.getFriendlyName(), 4);
		MAP.put(OrderStatus.DELIVERY_IN_PROGRESS.getFriendlyName(), 5);
		MAP.put(OrderStatus.DELIVERED.getFriendlyName(), 6);
		

		DELIVERY_MAP.put(1, DEFAULT_DELIVERY);
		DELIVERY_MAP.put(2, DEFAULT_DELIVERY);
		DELIVERY_MAP.put(3,PROVIDER_PACKED_DELIVERY);
		DELIVERY_MAP.put(4, DELIVERY_STARTED_BY_PROVIDER);
		DELIVERY_MAP.put(5, IN_PROGRESS_DELIVERY);
		DELIVERY_MAP.put(6, DELIVERED_PRODUCT);
		
	}
	
	public static int getCurrentStatus(String status){
		return MAP.get(status);
	}
	public static String getStatus(int priority){
		if(priority>6 || priority<1){
			return "IN_VALID";
		}
		String returnStatus="";
		for (Entry<String, Integer> status : MAP.entrySet()) {
			if(status.getValue()==priority){
				returnStatus =  status.getKey();
			}
		}
		return returnStatus;
	}
	
	public static Delivery getDelivery(int status){
		return DELIVERY_MAP.get(status);
	}
	
	
}
