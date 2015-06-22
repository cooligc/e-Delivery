package com.skc.delivery.controller;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.skc.auth.connect.FBConnection;
import org.skc.auth.connect.FBGraph;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.skc.delivery.db.mongo.service.UserService;
import com.skc.delivery.db.mongo.user.User;
import com.skc.delivery.model.Register;
import com.skc.delivery.utils.UserUtils;

/**
 * <p> In this Controller, We'll do needful for authentication </p>
 * @author IgnatiusCipher
 * */
@Controller
public class LoginController {
	private static final Logger LOGGER = Logger.getLogger(LoginController.class);
	
	@Resource(name="userService")
	UserService userService;
	
	@Resource(name="fb-connection")
    FBConnection connection;
	
	@Resource(name="fb-graph")
	FBGraph graph;
	
	
	@RequestMapping(value="/register",method=RequestMethod.POST)
	public String registerUser(HttpServletRequest request,@ModelAttribute("register") Register user){
		HttpSession httpSession = request.getSession();
		@SuppressWarnings("unchecked")
		Map<String,String> map= (Map<String, String>) httpSession.getAttribute("fb-details");
		httpSession.removeAttribute("fb-details");
		User registerUser = UserUtils.convertRegisterToUser(user,map);
		userService.createUser(registerUser);
		return "register/success";
	}
	
	@RequestMapping("/fb-auth")
	public String fbAuth(HttpServletRequest request,@ModelAttribute("register") Register register){
		LOGGER.info("Got the response from Facebook ");
		String code = request.getParameter("code");
		if (code == null || code.equals("")) {
			throw new RuntimeException(
					"ERROR: Didn't get code parameter in callback.");
		}
		String accessToken = connection.getAccessToken(code);
		
		graph.setAccessToken(accessToken);
		String graphData = graph.getFBGraph();
		Map<String, String> fbProfileData = graph.getGraphData(graphData);
		request.getSession().setAttribute("fb-details", fbProfileData);
		register.setEmail(fbProfileData.get("email"));
		register.setFirstname(fbProfileData.get("first_name"));
		register.setLastname(fbProfileData.get("last_name"));
		LOGGER.info("Facebook Data "+fbProfileData);
		return "register/register";		
	}
	
	@RequestMapping("/fb-dummy")
	public String createFBMockToken(HttpServletRequest request,HttpServletResponse response) throws IOException{
		response.sendRedirect("http://localhost:8080/fb-auth?code=dummy-fb-response-code");
		return null;
	}

	@RequestMapping("/user/login")
	public String login(HttpServletRequest request){
		LOGGER.info("User is going for Login");
		String successLogin = request.getParameter("successUrl");
		String requestedUrl = connection.getFBAuthUrl();
		LOGGER.info("FB Url is "+requestedUrl);
		request.setAttribute("login_url", requestedUrl);
		
		if(!StringUtils.isEmpty(successLogin)){
			request.setAttribute("successUrl", successLogin);
		}
		return "login";
	}
	
	@RequestMapping("/user/logout")
	public String logout(HttpServletRequest request){
		LOGGER.info("User is going out");
		request.setAttribute("message", "You are successfully Logged out from the System");
		String successLogin = request.getParameter("successUrl");
		if(!StringUtils.isEmpty(successLogin)){
			request.setAttribute("successUrl", successLogin);
		}
		return "index";
	}
	
	@RequestMapping(value="/error",method=RequestMethod.GET)
	public String errorPage(HttpServletRequest request){
		request.setAttribute("authError", "Invalid username or password");
		return "login";
	}
}
