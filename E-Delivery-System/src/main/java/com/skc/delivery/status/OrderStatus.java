package com.skc.delivery.status;

public class OrderStatus {
	
	private String status;
	private String friendlyName;
	
	public OrderStatus(String status, String friendlyName) {
		this.status=status;
		this.friendlyName=friendlyName;
	}
	

	public final static OrderStatus APPROVED = new OrderStatus("approved","APPROVED");
	public final static OrderStatus IN_PROGRESS = new OrderStatus("in-progress", "IN_PROGRESS");
	public final static OrderStatus PROVIDER_PACKED = new OrderStatus("provider-packed","PROVIDER_PACKED");
	public final static OrderStatus DELIVERY_STARTED = new OrderStatus("delivery-started", "DELIVERY_STARTED");
	public final static OrderStatus DELIVERY_IN_PROGRESS = new OrderStatus("delivery-in-progress","DELIVERY_IN_PROGRESS");
	public final static OrderStatus DELIVERED = new OrderStatus("delivered", "DELIVERED");
	

	/**
	 * @return the friendlyName
	 */
	public String getFriendlyName() {
		return friendlyName;
	}

	/**
	 * @param friendlyName the friendlyName to set
	 */
	public void setFriendlyName(String friendlyName) {
		this.friendlyName = friendlyName;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

}
