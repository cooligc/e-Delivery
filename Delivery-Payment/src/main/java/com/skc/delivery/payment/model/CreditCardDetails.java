package com.skc.delivery.payment.model;

public class CreditCardDetails {

	private String creaditCardNumber;
	private String creditCardType;
	private String ordererName;
	private String expiryMonth;
	private String expiryYear;
	private String cvvNumber;
	private String productName;
	private String totalPrice;
	private String address;
	/**
	 * @return the creaditCardNumber
	 */
	public String getCreaditCardNumber() {
		return creaditCardNumber;
	}
	/**
	 * @param creaditCardNumber the creaditCardNumber to set
	 */
	public void setCreaditCardNumber(String creaditCardNumber) {
		this.creaditCardNumber = creaditCardNumber;
	}
	/**
	 * @return the ordererName
	 */
	public String getOrdererName() {
		return ordererName;
	}
	/**
	 * @param ordererName the ordererName to set
	 */
	public void setOrdererName(String ordererName) {
		this.ordererName = ordererName;
	}
	/**
	 * @return the expiryMonth
	 */
	public String getExpiryMonth() {
		return expiryMonth;
	}
	/**
	 * @param expiryMonth the expiryMonth to set
	 */
	public void setExpiryMonth(String expiryMonth) {
		this.expiryMonth = expiryMonth;
	}
	/**
	 * @return the expiryYear
	 */
	public String getExpiryYear() {
		return expiryYear;
	}
	/**
	 * @param expiryYear the expiryYear to set
	 */
	public void setExpiryYear(String expiryYear) {
		this.expiryYear = expiryYear;
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
	 * @return the totalPrice
	 */
	public String getTotalPrice() {
		return totalPrice;
	}
	/**
	 * @param totalPrice the totalPrice to set
	 */
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}
	/**
	 * @return the cvvNumber
	 */
	public String getCvvNumber() {
		return cvvNumber;
	}
	/**
	 * @param cvvNumber the cvvNumber to set
	 */
	public void setCvvNumber(String cvvNumber) {
		this.cvvNumber = cvvNumber;
	}
	/**
	 * @return the creditCardType
	 */
	public String getCreditCardType() {
		return creditCardType;
	}
	/**
	 * @param creditCardType the creditCardType to set
	 */
	public void setCreditCardType(String creditCardType) {
		this.creditCardType = creditCardType;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	

}
