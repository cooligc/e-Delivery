package com.skc.delivery.db.mongo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.skc.delivery.db.mongo.product.Product;
import com.skc.delivery.db.mongo.product.Product.Rating;

@Service("productService")
public class ProductService {
	
	@Autowired
	MongoTemplate template;
	
	public List<Product> fetchProducts(){
		MongoOperations mongoOperations = template;
		return mongoOperations.findAll(Product.class);
	}
	
	public void insertProduct(Product product){
		MongoOperations mongoOperations = template;
		mongoOperations.insert(product);
	}
	
	public void updateProductRating(Rating rating){
		MongoOperations mongoOperations = template;
		Product existProduct = fetchProductByName(rating.getProductName()); 
		List<Rating> ratings = existProduct.getRatings();
		if(null == ratings){
			ratings = new ArrayList<Product.Rating>();
		}
		ratings.add(rating);
		Update update = new Update();
		update.set("ratings", ratings);
		mongoOperations.updateFirst(new Query(Criteria.where("name").is(rating.getProductName())), update, Product.class);
	}
	
	public Product fetchProductByName(String name){
		MongoOperations mongoOperations = template;
		Product product= mongoOperations.findOne(new Query(Criteria.where("name")
				.is(name)), Product.class);
		return product;
	}

	public void createDefaultProduct() {
		MongoOperations mongoOperations = template;
		Product product = new Product();
		product.setDescription("Dummy T-Shirt");
		product.setName("T-Shirt1");
		product.setPrice("10");
		product.setProductUrl("/img/t-shirt.jpg");
		mongoOperations.save(product);
		
		Product product2 = new Product();
		product2.setDescription("Dummy T-Shirt");
		product2.setName("T-Shirt2");
		product2.setPrice("10");
		product2.setProductUrl("/img/t-shirt1.jpg");
		mongoOperations.save(product2);
		
		
	}
}
