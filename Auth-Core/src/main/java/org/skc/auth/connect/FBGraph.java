package org.skc.auth.connect;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * <p> Here We can extract the data from the Facebook Server</p>
 * @author IgnatiusCipher
 * */
@Component("fb-graph")
public class FBGraph {
	private static final Logger LOGGER = Logger.getLogger(FBGraph.class);
	
	/*static{
		System.setProperty("http.proxyHost", "10.99.2.52");
		System.setProperty("http.proxyPort", "8080");
	}*/
	
	@Value("${offline}")
	Boolean isOffline;
	
	@Value("${fb-response}")
	String fbDummyResponse;
	
	private String accessToken;
	
	
	
	/**
	 * @param accessToken the accessToken to set
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * @return the accessToken
	 */
	public String getAccessToken() {
		return accessToken;
	}

	public String getFBGraph() {
		String graph = null;
		try {
			if(!isOffline){
				String g = "https://graph.facebook.com/me?" + accessToken;
				URL u = new URL(g);
				URLConnection c = u.openConnection();
				BufferedReader in = new BufferedReader(new InputStreamReader(
						c.getInputStream()));
				String inputLine;
				StringBuffer b = new StringBuffer();
				while ((inputLine = in.readLine()) != null)
					b.append(inputLine + "\n");
				in.close();
				graph = b.toString();
			}else{
				graph=fbDummyResponse;
			}
			
			LOGGER.info("Data got from fb-connection from graph"+graph);
		} catch (Exception e) {
			LOGGER.error(e);
			throw new RuntimeException("ERROR in getting FB graph data. " + e);
		}
		return graph;
	}

	public Map<String, String> getGraphData(String fbGraph) {
		Gson gson = new Gson();//.toJson(fbGraph);
		/*Ty stringStringMap = new TypeToken<Map<String, String>>(){}.getType();
		Map<String,String> fbProfile = gson.fromJson(gson, stringStringMap);		*/
		Map<String, String> fbProfile = gson.fromJson(fbGraph, new TypeToken<Map<String, String>>(){}.getType());//fromJson(gson, new TypeToken<Map<String, String>>(){}.getType());
		return fbProfile;
	}
}
