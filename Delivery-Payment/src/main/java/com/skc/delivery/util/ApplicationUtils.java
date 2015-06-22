package com.skc.delivery.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.PayPalResource;

public class ApplicationUtils {
	private static final Logger LOGGER = Logger.getLogger(ApplicationUtils.class);
	
	/**
	 * Constraints
	 * */
	public static final String DATE_DD_MM_YYYY_HH_MM_SS_S="ddMMyyyyhhmmssS";
	public static final String DATE_DD_MM_YYYY_HH_MM_SS="yyyy-MM-dd HH:mm:ss";
	
	/**
	 * Utility Method
	 * */
	public static String createOrderNumber(){
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_DD_MM_YYYY_HH_MM_SS_S);
		return dateFormat.format(new Date());
	}
	
	public static String getCurrentTimeStamp(){
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_DD_MM_YYYY_HH_MM_SS);
		return dateFormat.format(new Date());
	}

	public static Map<String, Object> convertPayment(Payment status) {
		Map<String, Object> paymentMap = new HashMap<String,Object>();
		paymentMap.put("create_time", status.getCreateTime());
		paymentMap.put("experienced_profile", status.getExperienceProfileId());
		paymentMap.put("payment_id", status.getId());
		paymentMap.put("intent", status.getIntent());
		paymentMap.put("state", status.getState());
		paymentMap.put("updated_time", status.getUpdateTime());
		if(null != status.getPayee()){
			JSONObject payeeDetails = new JSONObject(status.getPayee());
			Map<String, String> payee = createMapForObject(payeeDetails);
			paymentMap.put("payee_details", payee);
		}
		Set<JSONObject> transactions = new LinkedHashSet<JSONObject>(); 
		for (Transaction transaction : status.getTransactions()) {
			JSONObject txn = new JSONObject(transaction.toJSON());
			transactions.add(txn);
		}
		Map<String,Map<String,String>> trnx = new HashMap<String, Map<String,String>>();
		int i=0;
		for (JSONObject txnObject : transactions) {
			++i;
			Map<String,String> transaction = createMapForObject(txnObject);
			trnx.put("transaction"+i, transaction);
		}
		i=0;
		paymentMap.put("transactions_details",trnx);
		paymentMap.put("payment_amount", status.getCart());
		
		if(null != status.getRedirectUrls()){
			JSONObject redirect_url = new JSONObject(status.getRedirectUrls());
			Map<String,String> redirected_url = createMapForObject(redirect_url);
			paymentMap.put("redirect_url",redirected_url );
		}
		paymentMap.put("last_request", PayPalResource.getLastRequest());
		paymentMap.put("last_response", PayPalResource.getLastResponse());
		
		if(null != status.getPayer()){
			JSONObject payer = new JSONObject(status.getPayer());
			Map<String,String> payerMap = createMapForObject(payer);
			paymentMap.put("payeer_details", payerMap);
		}
		if(null != status.getLinks()){
			Map<String,Map<String,String>> linksMap = new HashMap<String, Map<String,String>>();
			for(Links link : status.getLinks()){
				++i;
				JSONObject linkData = new JSONObject(link.toJSON());
				linksMap.put("link"+i, createMapForObject(linkData));
			}
			paymentMap.put("payment_links", linksMap);
		}
		LOGGER.info("Payment Details "+paymentMap);
		return paymentMap;
	}

	private static Map<String, String> createMapForObject(
			JSONObject payeeDetails) {
		Map<String,String> payee = new HashMap<String, String>();
		for (Object objKey : payeeDetails.keySet()) {
			payee.put(objKey+"", payeeDetails.get(objKey.toString())+"");
		}
		return payee;
	}
}
