package com.springBoot.todoWebApp.todo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;

@Service
public class TodoService {
	
	private static List<Todo> todos = new ArrayList<>();
	
	private static int todocount=0;
	
	static {
		todos.add(new Todo(++todocount,"manja","Get AWS Certified",LocalDate.now().plusYears(1),false));
		todos.add(new Todo(++todocount,"manja","Learn DevOps",LocalDate.now().plusYears(2),false));
		todos.add(new Todo(++todocount,"manja","Learn Full Stack Development",LocalDate.now().plusYears(3),false));
	}
	
	public List<Todo> findByUsers(String username){
		Predicate<? super Todo> predicate = todo -> todo.getUsername().equalsIgnoreCase(username);
		return todos.stream().filter(predicate).toList();
		
	}
	
	public void addTodo(String username, String description, LocalDate targetDate, boolean done) {
		Todo todo=new Todo(++todocount,username,description,targetDate,done);
		todos.add(todo);
	}
	
	public void deleteById(int id) {
		Predicate<? super Todo> predicate = todo -> todo.getId()==id;
		todos.removeIf(predicate);
	}
	
	public Todo findById(int id) {
		Predicate<? super Todo> predicate = todo -> todo.getId()==id;
		Todo todo=todos.stream().filter(predicate).findFirst().get();
		return todo;
	}

	public void updateTodo(@Valid Todo todo) {
		deleteById(todo.getId());
		todos.add(todo);
	}

}

