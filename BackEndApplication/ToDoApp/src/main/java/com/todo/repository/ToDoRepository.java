package com.todo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todo.modal.ToDo;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo, Long> {
    // You can define custom query methods here if needed
	
	// To get the project Id using Project Entity
	List<ToDo> findByProjectProjectId(Long projectId);
	
	// To remove the project Id using Project Entity
	void deleteByProjectProjectId(Long projectId);
}
