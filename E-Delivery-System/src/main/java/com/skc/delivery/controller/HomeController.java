package com.skc.delivery.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.skc.delivery.db.mongo.product.Product;
import com.skc.delivery.db.mongo.service.ProductService;

/**
 * <p>
 * Home Controller . We can place some CMS Configuration.
 * </p>
 * 
 * @author IgnatiusCipher
 * */
@Controller
@RequestMapping("/")
public class HomeController {


	private static final Logger LOGGER = Logger.getLogger(HomeController.class);

	private static final String INDEX_PAGE = "index";
	
	@Resource(name="productService")
	ProductService productService;
	
	@RequestMapping(method = RequestMethod.GET)
	public String landHomePage(HttpServletRequest request) {
		List<Product> products = productService.fetchProducts();
		if(products.size()==0){
			productService.createDefaultProduct();
			LOGGER.info("Default Product is created");
			request.setAttribute("message", "Currently There are no product available. Please try after some time");
		}else{
			request.setAttribute("products", products);
		}
		return INDEX_PAGE;
	}
	

}
