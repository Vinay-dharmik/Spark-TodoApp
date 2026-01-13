package com.todo.todoapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.todo.todoapp.entity.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByCompleted(boolean completed);
    List<Todo> findByCategory(String category);
    List<Todo> findByPriority(Todo.Priority priority);
    List<Todo> findByCategoryAndCompleted(String category, boolean completed);
    long countByCompleted(boolean completed);
}