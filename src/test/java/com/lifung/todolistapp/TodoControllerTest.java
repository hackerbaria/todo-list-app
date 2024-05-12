package com.lifung.todolistapp;

import com.lifung.todolistapp.controller.TodoController;
import com.lifung.todolistapp.model.TodoItem;
import com.lifung.todolistapp.repository.TodoItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class TodoControllerTest {

    @Mock
    private TodoItemRepository todoItemRepository;

    @InjectMocks
    private TodoController todoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateTodoItem() {
        TodoItem todoItem = new TodoItem();
        when(todoItemRepository.save(any())).thenReturn(todoItem);

        ResponseEntity<TodoItem> response = todoController.createTodoItem(todoItem);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(todoItem, response.getBody());
    }

    @Test
    void testAssignTodoItemToUser() {
        TodoItem todoItem = new TodoItem();
        when(todoItemRepository.findById(anyLong())).thenReturn(Optional.of(todoItem));

        ResponseEntity<?> response = todoController.assignTodoItemToUser(1L, 1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(todoItemRepository, times(1)).save(any());
    }

    @Test
    void testGetTodoItemsByUserId() {
        List<TodoItem> todoItemList = new ArrayList<>();
        when(todoItemRepository.findByUserId(anyLong())).thenReturn(todoItemList);

        ResponseEntity<List<TodoItem>> response = todoController.getTodoItemsByUserId(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(todoItemList, response.getBody());
    }

    @Test
    void testAssignTodoItemToUser_NotFound() {
        when(todoItemRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<?> response = todoController.assignTodoItemToUser(1L, 1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(todoItemRepository, never()).save(any());
    }

    @Test
    void testMarkTodoItemAsCompleted() {
        TodoItem todoItem = new TodoItem();
        when(todoItemRepository.findById(anyLong())).thenReturn(Optional.of(todoItem));

        ResponseEntity<?> response = todoController.markTodoItemAsCompleted(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, todoItem.isCompleted());
        verify(todoItemRepository, times(1)).save(any());
    }

    @Test
    void testMarkTodoItemAsCompleted_NotFound() {
        when(todoItemRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResponseEntity<?> response = todoController.markTodoItemAsCompleted(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(todoItemRepository, never()).save(any());
    }
}
