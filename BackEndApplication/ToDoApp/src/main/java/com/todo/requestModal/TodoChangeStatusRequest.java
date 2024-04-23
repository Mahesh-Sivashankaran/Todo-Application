package com.todo.requestModal;

public class TodoChangeStatusRequest {
	private long toId;
    private String status;

    
    // Getters and setters

	public long getToId() {
		return toId;
	}

	public void setToId(long toId) {
		this.toId = toId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
