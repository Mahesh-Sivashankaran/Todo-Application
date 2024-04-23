package com.todo.service;

import java.time.LocalDate;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.todo.modal.Project;
import com.todo.modal.ToDo;
import com.todo.repository.ToDoRepository;

@Service
@Transactional
public class ToDoService {
	
	private final ToDoRepository toDoRepository;

    @Autowired
    public ToDoService(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    public List<ToDo> getToDos(long projectId) {
        return toDoRepository.findByProjectProjectId(projectId);
    }
    
    public void addToDo(long proId, String description, String status, String createdDate, String updatedDate) {
        ToDo toDo = new ToDo();
        toDo.setProject(new Project(proId)); // Project constructor accepts proId
        toDo.setDescription(description);
        toDo.setStatus(status);
        toDo.setCreatedDate(createdDate);
        toDo.setUpdatedDate(updatedDate);

        toDoRepository.save(toDo);
    }
    
    public void updateToDo(long toId, String description) {
        ToDo toDo = toDoRepository.findById(toId).orElse(null);
        if (toDo != null) {
            toDo.setDescription(description);
            toDoRepository.save(toDo);
            updateDate(toId);
        } else {
        	throw new IllegalArgumentException("ToDo with ID " + toId + " not found.");
        }
    }
    
    public void changeStatus(long toId, String status) {
        ToDo toDo = toDoRepository.findById(toId).orElse(null);
        if (toDo != null) {
            toDo.setStatus(status);
            toDoRepository.save(toDo);
            updateDate(toId);
        } else {
        	throw new IllegalArgumentException("ToDo with ID " + toId + " not found.");
        }
    }
    
    public void updateDate(long toId) {
    	String updatedDate = String.valueOf(LocalDate.now());
        ToDo toDo = toDoRepository.findById(toId).orElse(null);
        if (toDo != null) {
            toDo.setUpdatedDate(updatedDate);
            toDoRepository.save(toDo);
        } else {
        	throw new IllegalArgumentException("ToDo with ID " + toId + " not found.");
        }
    }
    
    public void deleteToDo(long toId) {
        toDoRepository.deleteById(toId);
    }
    
    public long getProId(long toId) {
        ToDo toDo = toDoRepository.findById(toId).orElse(null);
        if (toDo != null) {
            return toDo.getProject().getProId();
        } else {
            return -1;
        }
    }
}
