package com.skc.delivery.model;

import java.util.ArrayList;
import java.util.List;

public class Register {
	private String firstname;
	private String lastname;
	private String email;
	private String username;
	private String password;
	private String type;
	private List<String> allType;
	
	public Register(){
		setAllType(new ArrayList<String>());
	}
	/**
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}
	/**
	 * @param firstname the firstname to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	/**
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}
	/**
	 * @param lastname the lastname to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
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
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the allType
	 */
	public List<String> getAllType() {
		return allType;
	}
	/**
	 * @param allType the allType to set
	 */
	public void setAllType(List<String> allType) {
		this.allType = allType;
		allType.add("user");
		allType.add("delivery");
	}
	
	
}
