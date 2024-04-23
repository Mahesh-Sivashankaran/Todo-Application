package com.todo.service;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.todo.modal.Project;
import com.todo.modal.ToDo;
import com.todo.repository.ToDoRepository;

@TestInstance(Lifecycle.PER_CLASS)
public class ToDoServiceTest {

	private static final long PROJECT_ID = 1;

	private static final String STATUS = "pending";

	private static final String DATE = "2024-04-22";

	private static final String DESCRIPTION = "Project 1";
	
	private static final long TODO_ID = 1;
	
	private static final long IN_VALID_TODO_ID = 0;
	
	ToDoService todoService;
	
	@Mock
	ToDoRepository mockTodoRepository;
	
	@BeforeAll
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		todoService=new ToDoService(mockTodoRepository);
	}
	
	@Test
	public void getToDos() {
		List<ToDo> todos= new ArrayList<ToDo>();
		ToDo todo=new ToDo();
		todo.setToId(TODO_ID);
		todo.setDescription(DESCRIPTION);
		todo.setCreatedDate(DATE);
		todo.setStatus(STATUS);
		todos.add(todo);
		when(mockTodoRepository.findByProjectProjectId(PROJECT_ID)).thenReturn(todos);
		assertEquals(todos, todoService.getToDos(PROJECT_ID));
	}
	
	@Test
	public void addToDo() {
		todoService.addToDo(PROJECT_ID, DESCRIPTION, STATUS, DATE, DATE);
		verify(mockTodoRepository, times(3)).save(any(ToDo.class));
	}
	
	@Test
	public void updateToDo_ToDo_Id_Valid() {
		ToDo todo = new ToDo();
		Optional<ToDo> toDo=Optional.of(todo);
		when(mockTodoRepository.findById(TODO_ID)).thenReturn(toDo);
		todoService.updateToDo(TODO_ID, DESCRIPTION);
		verify(mockTodoRepository, times(5)).findById(TODO_ID);
		verify(mockTodoRepository, times(5)).save(any(ToDo.class));
	}
	
	@Test
	public void updateToDo_ToDo_Id_Not_Present() {
		Optional<ToDo> toDo=Optional.empty();
		when(mockTodoRepository.findById(IN_VALID_TODO_ID)).thenReturn(toDo);
		assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(()->todoService.updateToDo(IN_VALID_TODO_ID, DESCRIPTION))
        .withMessage("ToDo with ID " + IN_VALID_TODO_ID + " not found."); 
	}
	
	@Test
	public void changeStatus_ToDo_Id_Valid() {
		ToDo todo = new ToDo();
		todo.setToId(TODO_ID);
		todo.setStatus(STATUS);
		Optional<ToDo> toDo=Optional.of(todo);
		when(mockTodoRepository.findById(TODO_ID)).thenReturn(toDo);
		todoService.changeStatus(TODO_ID, STATUS);
		verify(mockTodoRepository, times(2)).findById(TODO_ID);
		verify(mockTodoRepository, times(2)).save(any(ToDo.class));
	}
	
	@Test
	public void changeStatus_ToDo_Id_Not_Present() {
		Optional<ToDo> toDo=Optional.empty();
		when(mockTodoRepository.findById(IN_VALID_TODO_ID)).thenReturn(toDo);
		assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(()->todoService.changeStatus(IN_VALID_TODO_ID, STATUS))
        .withMessage("ToDo with ID " + IN_VALID_TODO_ID + " not found."); 
	}
	
	@Test
	public void updateDate_ToDo_Id_Not_Present() {
		Optional<ToDo> toDo=Optional.empty();
		when(mockTodoRepository.findById(IN_VALID_TODO_ID)).thenReturn(toDo);
		assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(()->todoService.updateDate(IN_VALID_TODO_ID))
        .withMessage("ToDo with ID " + IN_VALID_TODO_ID + " not found."); 
	}
	
	@Test
	public void deleteToDo() {
		todoService.deleteToDo(TODO_ID);
		verify(mockTodoRepository, times(1)).deleteById(TODO_ID);
    }
	
	@Test
	public void getProId_ToDo_Id_Is_Present() {
		ToDo todo = new ToDo();
		todo.setToId(TODO_ID);
		todo.setDescription(DESCRIPTION);
		todo.setCreatedDate(DATE);
		todo.setProject(new Project(PROJECT_ID));
		Optional<ToDo> toDo=Optional.of(todo);
		when(mockTodoRepository.findById(TODO_ID)).thenReturn(toDo);
		assertEquals(PROJECT_ID, todoService.getProId(TODO_ID));
    }
	
	@Test
	public void getProId_ToDo_Id_Not_Present() {
		Optional<ToDo> todo = Optional.empty();
		when(mockTodoRepository.findById(IN_VALID_TODO_ID)).thenReturn(todo);
		assertEquals(-1, todoService.getProId(IN_VALID_TODO_ID));
    }
}
