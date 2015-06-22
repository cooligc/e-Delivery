package com.skc.delivery.db.mongo.product;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="product")
public class Product {
	private String name;
	private String productUrl;
	private String price;
	private String description;
	private List<Rating> ratings;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the productUrl
	 */
	public String getProductUrl() {
		return productUrl;
	}
	/**
	 * @param productUrl the productUrl to set
	 */
	public void setProductUrl(String productUrl) {
		this.productUrl = productUrl;
	}
	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * @return the ratings
	 */
	public List<Rating> getRatings() {
		return ratings;
	}
	/**
	 * @param ratings the ratings to set
	 */
	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}

	public static class Rating{
		private String rating;
		private String comment;
		private String userName;
		private String productName;
		private String commentTime;
		
		public Rating(String rating, String comment, String userName,String productName,String commentTime) {
			super();
			this.rating = rating;
			this.comment = comment;
			this.userName = userName;
			this.productName=productName;
			this.setCommentTime(commentTime);
		}
		
		public Rating(){}
		/**
		 * @return the rating
		 */
		public String getRating() {
			return rating;
		}
		/**
		 * @param rating the rating to set
		 */
		public void setRating(String rating) {
			this.rating = rating;
		}
		/**
		 * @return the comment
		 */
		public String getComment() {
			return comment;
		}
		/**
		 * @param comment the comment to set
		 */
		public void setComment(String comment) {
			this.comment = comment;
		}
		/**
		 * @return the userName
		 */
		public String getUserName() {
			return userName;
		}
		/**
		 * @param userName the userName to set
		 */
		public void setUserName(String userName) {
			this.userName = userName;
		}
		/**
		 * @return the productName
		 */
		public String getProductName() {
			return productName;
		}
		/**
		 * @param productName the productName to set
		 */
		public void setProductName(String productName) {
			this.productName = productName;
		}

		/**
		 * @return the commentTime
		 */
		public String getCommentTime() {
			return commentTime;
		}

		/**
		 * @param commentTime the commentTime to set
		 */
		public void setCommentTime(String commentTime) {
			this.commentTime = commentTime;
		}
		
		
	}
}
