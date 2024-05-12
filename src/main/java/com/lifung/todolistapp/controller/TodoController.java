package com.lifung.todolistapp.controller;

import com.lifung.todolistapp.model.TodoItem;
import com.lifung.todolistapp.repository.TodoItemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/todo")
public class TodoController {

    private final TodoItemRepository todoItemRepository;

    public TodoController(TodoItemRepository todoItemRepository) {
        this.todoItemRepository = todoItemRepository;
    }

    @PostMapping
    public ResponseEntity<TodoItem> createTodoItem(@RequestBody TodoItem todoItem) {
        TodoItem createdItem = todoItemRepository.save(todoItem);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdItem);
    }

    @PostMapping("/{todoId}/assign/{userId}")
    public ResponseEntity<?> assignTodoItemToUser(@PathVariable Long todoId, @PathVariable Long userId) {
        Optional<TodoItem> optionalTodoItem = todoItemRepository.findById(todoId);
        if (optionalTodoItem.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        TodoItem todoItem = optionalTodoItem.get();
        todoItem.setUserId(userId);
        todoItemRepository.save(todoItem);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{todoId}/complete")
    public ResponseEntity<?> markTodoItemAsCompleted(@PathVariable Long todoId) {
        Optional<TodoItem> optionalTodoItem = todoItemRepository.findById(todoId);
        if (optionalTodoItem.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        TodoItem todoItem = optionalTodoItem.get();
        todoItem.setCompleted(true);
        todoItemRepository.save(todoItem);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<TodoItem>> getTodoItemsByUserId(@PathVariable Long userId) {
        List<TodoItem> userTodoItems = todoItemRepository.findByUserId(userId);
        return ResponseEntity.ok(userTodoItems);
    }
}
