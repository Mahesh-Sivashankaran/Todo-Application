package com.todo.modal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Project {
    
    @Id
    @Column(name = "p_id")
    private long projectId;
    private String title;
    @Column(name = "created_date")
    private String createdDate;

    // Default constructor
    public Project() {
    	
    }
    
    public Project(long projectId) {
    	this.projectId=projectId;
    }

    public long getProId() {
        return projectId;
    }

    public void setProId(long proId) {
        this.projectId = proId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
