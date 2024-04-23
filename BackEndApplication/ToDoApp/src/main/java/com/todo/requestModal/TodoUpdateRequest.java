package com.todo.requestModal;

public class TodoUpdateRequest {
	private long toId;
    private String description;

    
    // Getters and setters

	public long getToId() {
		return toId;
	}

	public void setToId(long toId) {
		this.toId = toId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
