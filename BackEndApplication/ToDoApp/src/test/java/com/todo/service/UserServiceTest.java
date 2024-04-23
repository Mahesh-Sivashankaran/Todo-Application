package com.todo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.security.MessageDigest;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import com.todo.properties.MyProperties;
import com.todo.responseModal.UserStatusResponse;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	
	private static final String USERNAME = "admin";
	private static final String PASSWORD = "8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918";
	
	UserService userService;
	
	UserStatusResponse userStatus;
	
	MyProperties mockMyProperties;
	
	@Mock
	MessageDigest mockMessageDigest;
	
	
	@BeforeAll
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMyProperties=new MyProperties();
		userService=new UserService(mockMyProperties);
		userStatus=new UserStatusResponse();
	}
	
	
	@Test
	public void validateUser_Valid() {
		mockMyProperties.setUsername(USERNAME);
		mockMyProperties.setHashedPassword(PASSWORD);
		
		userStatus=userService.validateUser("admin", "admin");
		assertEquals("Valid", userStatus.getStatus());
	}
	
	@Test
	public void validateUser_InValid() {
		mockMyProperties.setUsername(USERNAME);
		mockMyProperties.setHashedPassword(PASSWORD+"ERR");
		
		userStatus=userService.validateUser("admin", "admin");
		assertEquals("Not Valid", userStatus.getStatus());
	}
}
