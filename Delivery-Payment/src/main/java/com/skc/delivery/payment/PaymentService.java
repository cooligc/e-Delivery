package com.skc.delivery.payment;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.CreditCard;
import com.paypal.api.payments.CreditCardToken;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.FundingInstrument;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.OAuthTokenCredential;
import com.paypal.base.rest.PayPalRESTException;
import com.skc.delivery.payment.model.CreditCardDetails;

@Service("paymentService")
public class PaymentService {
	
	private static final Logger LOGGER = Logger.getLogger(PaymentService.class);
	
	@Value("${clientId}")
	String clientId;//="AUSI4hDJWUsOAvOVjcYabnbZvkq91M3ycDnEVfCSeiVCb-cc73CZ_DX_fsy8";
	
	@Value("${clientSecret}")
	String secretId;//="EDHWahCT6jJqhj9Ix1ckrFMPSUzW-q82UXi8VNhDZL48bYW8-QHrYJoSXL5k";
	
	public static void main(String[] args) throws PayPalRESTException, IOException {
		InputStream inputStream = PaymentService.class.getResourceAsStream("/sdk_config.properties");
		Properties properties = new Properties();
		properties.load(inputStream);
		
		CreditCardDetails cardDetails = new CreditCardDetails();
		cardDetails.setCreaditCardNumber("5555555555554444");
		cardDetails.setCreditCardType("mastercard");
		cardDetails.setExpiryMonth("02");
		cardDetails.setExpiryYear("2023");
		cardDetails.setCvvNumber("234");
		cardDetails.setTotalPrice("10");
		PaymentService paymentService = new PaymentService();
		System.out.println(paymentService.placePayment(cardDetails));
		/*
		
		String clientId=properties.getProperty("clientId");"AUSI4hDJWUsOAvOVjcYabnbZvkq91M3ycDnEVfCSeiVCb-cc73CZ_DX_fsy8";
		String secretId=properties.getProperty("clientSecret");"EDHWahCT6jJqhj9Ix1ckrFMPSUzW-q82UXi8VNhDZL48bYW8-QHrYJoSXL5k";
		String clientAccessToken = new OAuthTokenCredential(clientId,secretId).getAccessToken();

		String  requestId = UUID.randomUUID().toString();
		APIContext apiContext = new APIContext(clientAccessToken,requestId);
		

		Details amountDetails = new Details();
		amountDetails.setShipping("2");
		amountDetails.setSubtotal("10");
		amountDetails.setTax("1");

		Amount amount = new Amount();
		amount.setCurrency("USD");
		Double total = Double.parseDouble("1")
				+ Double.parseDouble("2")
				+ Double.parseDouble("10");
		amount.setTotal(String.format("%.2f", total));
		amount.setDetails(amountDetails);

		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction.setDescription("Dummy Order");

		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(transaction);

		FundingInstrument fundingInstrument = new FundingInstrument();
		CreditCardToken creditCardToken = new CreditCardToken();
		creditCardToken.setCreditCardId("CARD-29K700850E316715MKWCA7GI");
		fundingInstrument.setCreditCardToken(creditCardToken);
		

		List<FundingInstrument> fundingInstrumentList = new ArrayList<FundingInstrument>();
		fundingInstrumentList.add(fundingInstrument);

		Payer payer = new Payer();
		payer.setFundingInstruments(fundingInstrumentList);
		payer.setPaymentMethod("credit_card");
		
		Links links = new Links();
		links.setHref("http://localhost:8080/redirectLink");
		links.setMethod("GET");
		Links links1 = new Links();
		links1.setHref("http://localhost:8080/redirectLink1");
		links1.setMethod("GET");
		Links links2 = new Links();
		links2.setHref("http://localhost:8080/redirectLink2");
		links2.setMethod("GET");
		List<Links> linkLists = new ArrayList<Links>();
		linkLists.add(links);
		linkLists.add(links1);
		linkLists.add(links2);
		
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl("http://localhost:8080/cancel");
		redirectUrls.setReturnUrl("http://localhost:8080/return");

		Payment payment = new Payment();
		payment.setIntent("sale");
		payment.setPayer(payer);
		payment.setRedirectUrls(redirectUrls);
		payment.setTransactions(transactions);
		payment.setCreateTime(new Date().toString());
		payment.setLinks(linkLists);
		Payment payment2 = payment.create(apiContext);
		System.out.println(payment2);
*/	}

	public Payment placePayment(CreditCardDetails details) {
		LOGGER.info("Entring for Payment");
		String clientAccessToken = createToken();
		if(StringUtils.isEmpty(clientAccessToken) || clientAccessToken.equals("invalid")){
			return null;
		}
		
		LOGGER.info("Creating Credit Card");
//		String accessToken = AccessTokenGenerator.getAccessToken();
		CreditCard creditCard = new CreditCard();
		creditCard.setExpireMonth(Integer.parseInt(details.getExpiryMonth().trim()));
		creditCard.setExpireYear(Integer.parseInt(details.getExpiryYear().trim()));
		creditCard.setNumber(details.getCreaditCardNumber());
		creditCard.setType(details.getCreditCardType());
		creditCard.setCvv2(Integer.parseInt(details.getCvvNumber()));
		CreditCard card;
		try {
			card = creditCard.create(clientAccessToken);
		} catch (PayPalRESTException e) {
			e.printStackTrace();
			LOGGER.error(e);
			return null;
		}
		
		String paymentAccessToken = createToken();
		if(StringUtils.isEmpty(paymentAccessToken) || clientAccessToken.equals("invalid")){
			return null;
		}
		
		String  requestId = UUID.randomUUID().toString();
		APIContext apiContext = new APIContext(paymentAccessToken,requestId);
		
		Details amountDetails = new Details();
		amountDetails.setShipping("0");
		amountDetails.setSubtotal(details.getTotalPrice());
		amountDetails.setTax("0");

		LOGGER.info("Amount Details Set "+amountDetails);
		Amount amount = new Amount();
		amount.setCurrency("USD");
		Double total = Double.parseDouble(details.getTotalPrice());
		amount.setTotal(String.format("%.2f", total));
		amount.setDetails(amountDetails);
		LOGGER.info("Amount Type Set "+amount);

		Transaction transaction = new Transaction();
		transaction.setAmount(amount);
		transaction.setDescription("Dummy Order");
		
		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(transaction);
		LOGGER.info("Transaction Token is added "+transaction);
		
		FundingInstrument fundingInstrument = new FundingInstrument();
		CreditCardToken creditCardToken = new CreditCardToken();
		creditCardToken.setCreditCardId(card.getId());
		fundingInstrument.setCreditCardToken(creditCardToken);
		LOGGER.info("FundingInstrument is set : "+fundingInstrument);

		List<FundingInstrument> fundingInstrumentList = new ArrayList<FundingInstrument>();
		fundingInstrumentList.add(fundingInstrument);
		LOGGER.info("FundingInstrument is added");

		Payer payer = new Payer();
		payer.setFundingInstruments(fundingInstrumentList);
		payer.setPaymentMethod("credit_card");
		LOGGER.info("Payer is set : "+payer);
		
		/*Links links = new Links();
		links.setHref("http://localhost:8080/redirectLink");
		links.setMethod("GET");
		Links links1 = new Links();
		links1.setHref("http://localhost:8080/redirectLink1");
		links1.setMethod("GET");
		Links links2 = new Links();
		links2.setHref("http://localhost:8080/redirectLink2");
		links2.setMethod("GET");
		List<Links> linkLists = new ArrayList<Links>();
		linkLists.add(links);
		linkLists.add(links1);
		linkLists.add(links2);
		LOGGER.info("Links are add")*/
		/*
		RedirectUrls redirectUrls = new RedirectUrls();
		redirectUrls.setCancelUrl("http://localhost:8080/cancel");
		redirectUrls.setReturnUrl("http://localhost:8080/return");*/

		Payment payment = new Payment();
		payment.setIntent("sale");
		payment.setPayer(payer);
		//payment.setRedirectUrls(redirectUrls);
		payment.setTransactions(transactions);
		payment.setCreateTime(new Date().toString());
		//payment.setLinks(linkLists);
		Payment payment2 = null;
		try {
			payment2 = payment.create(apiContext);
		} catch (PayPalRESTException e) {
			e.printStackTrace();
			LOGGER.error(e);
			return null;
		}
		return payment2;
	}

	private String createToken() {
		String clientAccessToken=null;
		try {
			clientAccessToken = new OAuthTokenCredential(clientId,secretId).getAccessToken();
		} catch (PayPalRESTException e) {
			LOGGER.error(e);
			return "invalid";
		}
		return clientAccessToken;
	}
}
