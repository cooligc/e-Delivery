package com.skc.delivery.utils;

import java.util.Map;

import com.skc.delivery.db.mongo.user.User;
import com.skc.delivery.model.Register;

public class UserUtils {

	public static User convertRegisterToUser(Register register,Map<String, String> map) {
		User user = new User();
		user.setEmail(register.getEmail());
		user.setFirstName(register.getFirstname());
		user.setLastName(register.getLastname());
		user.setPassword(register.getPassword());
		user.setRole(getUserRole(register));
		user.setMetadata(map);
		user.setUsername(register.getUsername());
		return user;
	}

	private static int getUserRole(Register register) {
		if(register.getType().equalsIgnoreCase("user")){
			return 2;
		}else if(register.getType().equalsIgnoreCase("delivery")){
			return 3;
		}else{
			return 1;
		}
	}

}
