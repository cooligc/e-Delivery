package org.skc.auth.connect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.apache.log4j.Logger;
import org.skc.auth.common.AuthConstraints;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * <p> This class will make a connection b/w our app to the facebook.com</p>
 * @author IgnatiusCipher
 * */

@Component("fb-connection")
public class FBConnection {
	private static final Logger LOGGER = Logger.getLogger(FBConnection.class);
	
	@Value("${offline}")
	Boolean isOffline;
	
	@Value("${fb-dummy-token}")
	String fbDummyToken;
	
	private String accessToken = "";
	
	
	public String getFBAuthUrl(){
		String fbLoginUrl="";
		try {
			fbLoginUrl = "http://www.facebook.com/dialog/oauth?" + "client_id="
					+ AuthConstraints.APP_ID+ "&redirect_uri="
					+ URLEncoder.encode(AuthConstraints.AUTH_REDIRECT_URL, "UTF-8")
					+ "&scope=email";
		} catch (UnsupportedEncodingException e) {
			LOGGER.error(e);
		}
		if(isOffline){
			fbLoginUrl="http://localhost:8080/fb-dummy";
		}
		
		return fbLoginUrl;
	}
	
	public String getFBGraphUrl(String code) {
		String fbGraphUrl = "";
		try {
			fbGraphUrl = "https://graph.facebook.com/oauth/access_token?"
					+ "client_id=" + AuthConstraints.APP_ID + "&redirect_uri="
					+ URLEncoder.encode(AuthConstraints.AUTH_REDIRECT_URL, "UTF-8")
					+ "&client_secret=" + AuthConstraints.APP_SECRET + "&code=" + code;
		} catch (UnsupportedEncodingException e) {
			LOGGER.error(e);
		}
		return fbGraphUrl;
	}
	
	public String getAccessToken(String code) {
		if ("".equals(accessToken)) {
			URL fbGraphURL;
			try {
				if(!isOffline){
					fbGraphURL = new URL(getFBGraphUrl(code));
				}else{
					fbGraphURL=new URL("http://localhost:8080/dummy-graph-url");
				}
			} catch (MalformedURLException e) {
				LOGGER.error(e);
				throw new RuntimeException("Invalid code received " + e);
			}
			URLConnection fbConnection;
			StringBuffer b = null;
			try {
				if(!isOffline){
					fbConnection = fbGraphURL.openConnection();
					BufferedReader in;
					in = new BufferedReader(new InputStreamReader(
							fbConnection.getInputStream()));
					String inputLine;
					b = new StringBuffer();
					while ((inputLine = in.readLine()) != null)
						b.append(inputLine + "\n");
					in.close();
				}else{
					b=new StringBuffer(fbDummyToken);
				}
				
				LOGGER.info("Details got from facebook.com"+b);
			} catch (IOException e) {
				LOGGER.error(e);
				throw new RuntimeException("Unable to connect with Facebook "
						+ e);
			}

			accessToken = b.toString();
			if (accessToken.startsWith("{")) {
				LOGGER.error("Invalid Access Token "+accessToken);
				throw new RuntimeException("ERROR: Access Token Invalid: "
						+ accessToken);
			}
		}
		return accessToken;
	}
}
