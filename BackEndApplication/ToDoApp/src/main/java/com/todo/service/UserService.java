package com.todo.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todo.controller.UserController;
import com.todo.properties.MyProperties;
import com.todo.responseModal.UserStatusResponse;

@Service
public class UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private final MyProperties myProperties;
	
	@Autowired
    public UserService(MyProperties myProperties) {
        this.myProperties = myProperties;
    }
	
	@Autowired
	UserStatusResponse userstatus;
	
	public UserStatusResponse validateUser(String username, String password) {
		
		//Retrieve values from properties file
		String storedUsername=myProperties.getUsername();
		String storedPassword=myProperties.getHashedPassword();
		
		// Hashing password
		StringBuilder hexString = new StringBuilder();
		String userStatus="";
		try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            
            byte[] hashedBytes = digest.digest(password.getBytes());
            
            // Convert the byte array to a hexadecimal string representation
            for (byte b : hashedBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            
            password=hexString.toString();
            
            //Checking for Valid User
            if(username.equals(storedUsername) && password.equals(storedPassword)) {
            	userStatus="Valid";
            }
            else {
            	userStatus="Not Valid";
            }
            // Log the User Status
            logger.info("User Status: " + userStatus);
        } catch (NoSuchAlgorithmException e) {
        	logger.error("SHA-256 algorithm not available.");
        }
		if(userstatus==null) {
			userstatus=getUserStatus();
		}
		userstatus.setStatus(userStatus);
		
		return userstatus;
	}
	public UserStatusResponse getUserStatus() {
		return new UserStatusResponse();
	}
}
