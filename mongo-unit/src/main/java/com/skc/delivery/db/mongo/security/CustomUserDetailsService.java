package com.skc.delivery.db.mongo.security;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.skc.delivery.db.mongo.user.User;

/**
 * <p>
 * User Details Service for Security Purpose
 * </p>
 * 
 * @author IgnatiusCipher
 * */
@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	private static final Logger LOGGER = Logger.getLogger(CustomUserDetailsService.class);
	@Autowired
	MongoTemplate mongoTemplate;
	
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		User user = getUserDetail(username);
		LOGGER.info("User details "+user);
		org.springframework.security.core.userdetails.User userDetail = new org.springframework.security.core.userdetails.User(
				user.getUsername(), user.getPassword(), true, true, true, true,
				getAuthorities(user.getRole()));
		return userDetail;
	}

	private List<GrantedAuthority> getAuthorities(Integer role) {

		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
		if (role.intValue() == 1) {
			authList.add(new SimpleGrantedAuthority("ROLE_USER"));
			authList.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
			authList.add(new SimpleGrantedAuthority("ROLE_DELIVERY"));
		} else if (role.intValue() == 2) {
			authList.add(new SimpleGrantedAuthority("ROLE_USER"));
		} else if (role.intValue() == 3) {
			authList.add(new SimpleGrantedAuthority("ROLE_DELIVERY"));
		}
		return authList;
	}

	private User getUserDetail(String username) {

		MongoOperations mongoOperation = (MongoOperations) mongoTemplate;
		User user = mongoOperation.findOne(new Query(Criteria.where("username")
				.is(username)), User.class);
		return user;
	}

}
