package com.todo.requestModal;

public class ProjectUpdateRequest {
    private long proId;
    private String title;

    // Getters and setters
    public long getProId() {
        return proId;
    }

    public void setProId(long proId) {
        this.proId = proId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
