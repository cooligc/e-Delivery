package com.skc.delivery.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.skc.delivery.db.mongo.product.Product;
import com.skc.delivery.db.mongo.service.ProductService;
import com.skc.delivery.util.ApplicationUtils;

@Controller
public class ProductController {
	private static final Logger LOGGER = Logger.getLogger(ProductController.class);
	
	@Resource(name="productService")
	ProductService productService;
	
	@RequestMapping("/product/{name}")
	public String productDetails(Model model,@ModelAttribute("rating") Product.Rating rating,@PathVariable("name") String productName){
		Product product = productService.fetchProductByName(productName);
		LOGGER.info("Selected Product is "+productName);
		model.addAttribute("product", product);
		return "product/productDetails";
	}
	@RequestMapping(value="/rating",method=RequestMethod.POST)
	public String productRating(Model model,@ModelAttribute("rating") Product.Rating rating){
		UserDetails userDetails =
				 (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		rating.setCommentTime(ApplicationUtils.getCurrentTimeStamp());
		rating.setUserName(userDetails.getUsername());
		productService.updateProductRating(rating);
		Map<String,String> map = new HashMap<String, String>();
		map.put("status", "successful");
		model.addAttribute("product", productService.fetchProductByName(rating.getProductName()));
		return "product/productDetails";
		
	}
}
