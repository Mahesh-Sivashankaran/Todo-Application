package com.todo.responseModal;

import org.springframework.stereotype.Component;

@Component
public class UserStatusResponse {
	String status;
	
	public void setStatus(String status) {
		this.status=status;
	}
	
	public String getStatus() {
		return status;
	}
}
