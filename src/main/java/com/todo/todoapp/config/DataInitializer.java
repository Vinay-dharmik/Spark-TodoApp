package com.todo.todoapp.config;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.todo.todoapp.entity.Todo;
import com.todo.todoapp.repository.TodoRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {
    
    @Bean
    CommandLineRunner initDatabase(TodoRepository todoRepository) {
        return args -> {
            // Clear existing data and add fresh sample data
            todoRepository.deleteAll();
            
            // Sample todos with various priorities, categories, and statuses
            todoRepository.save(Todo.builder()
                .title("Welcome to SparkTodo! 🎉")
                .description("Your beautiful task manager. Complete this first task to see the celebration animation!")
                .completed(false)
                .priority(Todo.Priority.HIGH)
                .category("Getting Started")
                .dueDate(LocalDateTime.now().plusDays(1))
                .build());
            
            todoRepository.save(Todo.builder()
                .title("Explore the Dashboard")
                .description("Check out the beautiful stats cards and UI elements. Try hovering over them!")
                .completed(true)
                .priority(Todo.Priority.MEDIUM)
                .category("Tutorial")
                .dueDate(LocalDateTime.now().minusDays(1))
                .build());
            
            todoRepository.save(Todo.builder()
                .title("Add Your Own Tasks")
                .description("Use the form on the right side to add new tasks. Try different priorities and categories!")
                .completed(false)
                .priority(Todo.Priority.URGENT)
                .category("Personal")
                .dueDate(LocalDateTime.now().plusHours(3))
                .build());
            
            todoRepository.save(Todo.builder()
                .title("Organize with Categories")
                .description("Create categories like Work, Personal, Shopping to keep tasks organized")
                .completed(false)
                .priority(Todo.Priority.LOW)
                .category("Work")
                .dueDate(LocalDateTime.now().plusDays(7))
                .build());
            
            todoRepository.save(Todo.builder()
                .title("Set Priorities")
                .description("Mark tasks as Low, Medium, High, or Urgent priority to focus on what's important")
                .completed(true)
                .priority(Todo.Priority.HIGH)
                .category("Tips")
                .dueDate(LocalDateTime.now().plusDays(2))
                .build());
            
            todoRepository.save(Todo.builder()
                .title("Try Dark Mode UI")
                .description("Enjoy the beautiful dark theme with gradient accents and smooth animations")
                .completed(false)
                .priority(Todo.Priority.MEDIUM)
                .category("UI Features")
                .dueDate(LocalDateTime.now().plusDays(5))
                .build());
            
            todoRepository.save(Todo.builder()
                .title("Complete Tasks for Confetti! 🎊")
                .description("Click the checkbox to complete tasks and watch the celebration animation!")
                .completed(false)
                .priority(Todo.Priority.URGENT)
                .category("Fun")
                .dueDate(LocalDateTime.now().plusHours(1))
                .build());
            
            todoRepository.save(Todo.builder()
                .title("Responsive Design")
                .description("The app works beautifully on desktop, tablet, and mobile devices")
                .completed(true)
                .priority(Todo.Priority.LOW)
                .category("Features")
                .dueDate(LocalDateTime.now())
                .build());
            
            System.out.println("=========================================");
            System.out.println("✅ SparkTodo Application Started!");
            System.out.println("📊 Sample todos loaded: " + todoRepository.count());
            System.out.println("🌐 Access at: http://localhost:8080");
            System.out.println("🗄️  H2 Console: http://localhost:8080/h2-console");
            System.out.println("=========================================");
        };
    }
}