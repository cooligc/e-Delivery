package com.skc.delivery.db.mongo.order;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.skc.delivery.db.mongo.product.Product;

@Document(collection="order")
public class Order {
	@Id
	private String id;
	private String orderName;
	private String orderNumber;
	private String orderDate;
	private List<Product> products;
	private Map<String, Object> payment;
	private String status;
	private String username;
	private String orderTotal;
	private Set<Delivery> deliveries;
	private String address;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the orderName
	 */
	public String getOrderName() {
		return orderName;
	}
	/**
	 * @param orderName the orderName to set
	 */
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	/**
	 * @return the orderNumber
	 */
	public String getOrderNumber() {
		return orderNumber;
	}
	/**
	 * @param orderNumber the orderNumber to set
	 */
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	/**
	 * @return the orderDate
	 */
	public String getOrderDate() {
		return orderDate;
	}
	/**
	 * @param orderDate the orderDate to set
	 */
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	/**
	 * @return the products
	 */
	public List<Product> getProducts() {
		return products;
	}
	/**
	 * @param products the products to set
	 */
	public void setProducts(List<Product> products) {
		this.products = products;
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
	/**
	 * @return the payment
	 */
	public Map<String, Object> getPayment() {
		return payment;
	}
	/**
	 * @param map the payment to set
	 */
	public void setPayment(Map<String, Object> map) {
		this.payment = map;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the orderTotal
	 */
	public String getOrderTotal() {
		return orderTotal;
	}
	/**
	 * @param orderTotal the orderTotal to set
	 */
	public void setOrderTotal(String orderTotal) {
		this.orderTotal = orderTotal;
	}
	/**
	 * @return the delivery
	 */
	public Set<Delivery> getDelivery() {
		return deliveries;
	}
	/**
	 * @param delivery the delivery to set
	 */
	public void setDelivery(Set<Delivery> deliveries) {
		this.deliveries = deliveries;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
