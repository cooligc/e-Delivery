package com.skc.delivery.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.paypal.api.payments.Payment;
import com.skc.delivery.db.mongo.order.Delivery;
import com.skc.delivery.db.mongo.order.Order;
import com.skc.delivery.db.mongo.product.Product;
import com.skc.delivery.db.mongo.service.OrderService;
import com.skc.delivery.db.mongo.service.ProductService;
import com.skc.delivery.payment.PaymentService;
import com.skc.delivery.payment.model.CreditCardDetails;
import com.skc.delivery.status.OrderStatus;
import com.skc.delivery.util.ApplicationUtils;

@Controller
public class OrderController {

	@Resource(name="orderService")
	OrderService orderService;
	
	@Resource(name="productService")
	ProductService productService;
	
	@Resource(name="paymentService")
	PaymentService paymentService;
	
	@RequestMapping("/view-orders")
	public String showOrders(Model model){
		UserDetails userDetails =
				 (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		List<Order> orders = orderService.fetchOrder(userDetails.getUsername());
		model.addAttribute("orders", orders);
		return "order/viewOrder";
	}
	
	@RequestMapping("/view-orders/{ordernumber}")
	public String showOrder(Model model,@PathVariable("ordernumber") String orderNumber){
		Order orders = orderService.fetchOrderByOrderNumber(orderNumber);
		int percentage = orders.getDelivery().size()*100/6;
		model.addAttribute("order", orders);
		model.addAttribute("deliveries", orders.getDelivery());
		model.addAttribute("percentage", percentage);
		return "order/viewSingle";
	}
	
	@RequestMapping(value="/buy-now",method=RequestMethod.POST)
	public String placeOrder(Model model,@ModelAttribute("order") CreditCardDetails cardDetails,@RequestParam("name")String name){
		Product product = productService.fetchProductByName(name);
		cardDetails.setTotalPrice(product.getPrice());
		cardDetails.setProductName(name);
		return "order/placeOrder";
	}
	
	@RequestMapping(value="/placeOrder",method=RequestMethod.POST)
	public String placePaymentAndOrder(@ModelAttribute("order") CreditCardDetails cardDetails,Model model){
		UserDetails userDetails =
				 (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		
		Payment status = paymentService.placePayment(cardDetails);
		if(null == status){
			return "extra/error";
		}
		Order order = new Order();
		if(status.getState().equalsIgnoreCase("approved")){
    		order.setId(ApplicationUtils.createOrderNumber());
    		order.setOrderDate(ApplicationUtils.getCurrentTimeStamp());
    		order.setOrderName(cardDetails.getOrdererName());
    		order.setOrderNumber(ApplicationUtils.createOrderNumber());
    		order.setOrderTotal(cardDetails.getTotalPrice());
    		order.setUsername(userDetails.getUsername());
    		List<Product> products = new ArrayList<Product>();
    		products.add(productService.fetchProductByName(cardDetails.getProductName()));
    		order.setStatus(OrderStatus.APPROVED.getFriendlyName());
    		order.setPayment(ApplicationUtils.convertPayment(status));
    		order.setAddress(cardDetails.getAddress());
    		Set<Delivery> deliveries = new LinkedHashSet<Delivery>();
    		Delivery delivery = new Delivery();
    		delivery.setDeliveryTime(order.getOrderDate());
    		delivery.setStatus(order.getStatus());
    		delivery.setDescription("Order is going to initiate");
    		deliveries.add(delivery);
    		order.setDelivery(deliveries);
    		orderService.createOrder(order);
    	}
		model.addAttribute("message", "Your order number is "+order.getOrderNumber()+" successfully placed");
		List<Order> orders = orderService.fetchOrder(userDetails.getUsername());
		Collections.sort(orders, new Comparator<Order>() {
			public int compare(Order order1, Order order2) {
				return order1.getOrderDate().compareTo(order2.getOrderDate());
			}
		});
		
		model.addAttribute("orders",orders);
		return "order/viewOrder";
	}
}
