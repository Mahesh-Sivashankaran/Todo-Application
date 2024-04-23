package com.todo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.todo.modal.ToDo;
import com.todo.requestModal.TodoAddRequest;
import com.todo.requestModal.TodoChangeStatusRequest;
import com.todo.requestModal.TodoUpdateRequest;
import com.todo.service.ToDoService;


@RestController
@RequestMapping("/ToDo")
public class TodoController {
	
	private static final Logger logger = LoggerFactory.getLogger(TodoController.class);

	@Autowired
	private ToDoService toDoService;

	
	// To-Do Controller
	@RequestMapping(value = "/getToDos", method=RequestMethod.GET)
	public ResponseEntity<ToDoResponse> getToDos(@RequestParam("proId") long proId) {
        List<ToDo> todos = toDoService.getToDos(proId);
        ToDoResponse response = new ToDoResponse(todos);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private static class ToDoResponse {
        private List<ToDo> todos;

        public ToDoResponse(List<ToDo> todos) {
            this.todos = todos;
        }

        public List<ToDo> getTodos() {
            return todos;
        }

        public void setTodos(List<ToDo> todos) {
            this.todos = todos;
        }
    }
	
	@RequestMapping(value = "/addToDo", method=RequestMethod.POST)
	public ResponseEntity<ToDoResponse> addToDo(@RequestBody TodoAddRequest todoAddRequest) {
		long proId = todoAddRequest.getProId();
		String status = todoAddRequest.getStatus();
        String description = todoAddRequest.getDescription();
        String createdDate = todoAddRequest.getCreatedDate();
        String updatedDate = todoAddRequest.getUpdatedDate();
        
        logger.info("PROJECT ID: "+proId);
        toDoService.addToDo(proId,description,status,createdDate,updatedDate);
        
        return getToDos(proId);
	}
	
	@RequestMapping(value = "/updateToDo", method=RequestMethod.PUT)
	public ResponseEntity<ToDoResponse> updateTodo(@RequestBody TodoUpdateRequest todoUpdateRequest) {
		long toId = todoUpdateRequest.getToId();
        String description = todoUpdateRequest.getDescription();
		toDoService.updateToDo(toId,description);
		long proId = toDoService.getProId(toId);
		
		return getToDos(proId);
	}
	
	@RequestMapping(value = "/deleteToDo/{toId}", method=RequestMethod.DELETE)
	public ResponseEntity<ToDoResponse> deleteToDo(@PathVariable long toId) {
		long proId = toDoService.getProId(toId);
		toDoService.deleteToDo(toId);
		
		return getToDos(proId);
	}
	
	@RequestMapping(value = "/changeStatus", method=RequestMethod.PUT)
	public ResponseEntity<ToDoResponse>  changeStatus(@RequestBody TodoChangeStatusRequest todoChangeStatusRequest) {
		long toId = todoChangeStatusRequest.getToId();
        String status = todoChangeStatusRequest.getStatus();
		toDoService.changeStatus(toId,status);
		long proId = toDoService.getProId(toId);
		
		return getToDos(proId);
	}
}
