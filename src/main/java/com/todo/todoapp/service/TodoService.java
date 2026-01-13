package com.todo.todoapp.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todo.todoapp.entity.Todo;
import com.todo.todoapp.repository.TodoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TodoService {
    private final TodoRepository todoRepository;
    
    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }
    
    public Todo getTodoById(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found with id: " + id));
    }
    
    public Todo saveTodo(Todo todo) {
        if (todo.getCreatedAt() == null) {
            todo.setCreatedAt(LocalDateTime.now());
        }
        return todoRepository.save(todo);
    }
    
    public Todo createTodo(Todo todo) {
        return saveTodo(todo);
    }
    
    public Todo updateTodo(Long id, Todo todoDetails) {
        Todo todo = getTodoById(id);
        
        todo.setTitle(todoDetails.getTitle());
        todo.setDescription(todoDetails.getDescription());
        todo.setCompleted(todoDetails.isCompleted());
        todo.setDueDate(todoDetails.getDueDate());
        todo.setPriority(todoDetails.getPriority());
        todo.setCategory(todoDetails.getCategory());
        
        return todoRepository.save(todo);
    }
    
    public Todo updateTodoPartial(Long id, Todo todoDetails) {
        Todo todo = getTodoById(id);
        
        if (todoDetails.getTitle() != null) {
            todo.setTitle(todoDetails.getTitle());
        }
        if (todoDetails.getDescription() != null) {
            todo.setDescription(todoDetails.getDescription());
        }
        if (todoDetails.getDueDate() != null) {
            todo.setDueDate(todoDetails.getDueDate());
        }
        if (todoDetails.getPriority() != null) {
            todo.setPriority(todoDetails.getPriority());
        }
        if (todoDetails.getCategory() != null) {
            todo.setCategory(todoDetails.getCategory());
        }
        
        return todoRepository.save(todo);
    }
    
    public void deleteTodo(Long id) {
        if (!todoRepository.existsById(id)) {
            throw new RuntimeException("Todo not found with id: " + id);
        }
        todoRepository.deleteById(id);
    }
    
    public List<Todo> getTodosByStatus(boolean completed) {
        return todoRepository.findByCompleted(completed);
    }
    
    public Todo toggleTodoStatus(Long id) {
        Todo todo = getTodoById(id);
        todo.setCompleted(!todo.isCompleted());
        return todoRepository.save(todo);
    }
    
    public List<Todo> getTodosByCategory(String category) {
        return todoRepository.findByCategory(category);
    }
    
    public List<Todo> getTodosByPriority(Todo.Priority priority) {
        return todoRepository.findByPriority(priority);
    }
    
    public long getTotalCount() {
        return todoRepository.count();
    }
    
    public long getCompletedCount() {
        return todoRepository.countByCompleted(true);
    }
    
    public long getPendingCount() {
        return todoRepository.countByCompleted(false);
    }
    
    public List<Todo> searchTodos(String keyword) {
        return todoRepository.findAll().stream()
                .filter(todo -> 
                    todo.getTitle().toLowerCase().contains(keyword.toLowerCase()) ||
                    (todo.getDescription() != null && 
                     todo.getDescription().toLowerCase().contains(keyword.toLowerCase())) ||
                    (todo.getCategory() != null && 
                     todo.getCategory().toLowerCase().contains(keyword.toLowerCase()))
                )
                .toList();
    }
}