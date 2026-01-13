package com.todo.todoapp.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.todo.todoapp.entity.Todo;
import com.todo.todoapp.service.TodoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TodoController {
    private final TodoService todoService;
    
    @GetMapping("/")
    public String home() {
        return "redirect:/todos";
    }
    
    @GetMapping("/todos")
    public String getAllTodos(Model model) {
        List<Todo> todos = todoService.getAllTodos();
        long urgentCount = todos.stream()
                .filter(todo -> todo.getPriority() == Todo.Priority.URGENT)
                .count();
        
        model.addAttribute("todos", todos);
        model.addAttribute("newTodo", Todo.builder().build());
        model.addAttribute("totalCount", todoService.getTotalCount());
        model.addAttribute("completedCount", todoService.getCompletedCount());
        model.addAttribute("pendingCount", todoService.getPendingCount());
        model.addAttribute("urgentCount", urgentCount);
        
        return "index";
    }
    
    @PostMapping("/todos")
    public String createTodo(@ModelAttribute Todo todo) {
        todoService.createTodo(todo);
        return "redirect:/todos";
    }
    
    @PostMapping("/todos/{id}/toggle")
    public String toggleTodoStatus(@PathVariable Long id) {
        todoService.toggleTodoStatus(id);
        return "redirect:/todos";
    }
    
    @PostMapping("/todos/{id}/delete")
    public String deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return "redirect:/todos";
    }
}