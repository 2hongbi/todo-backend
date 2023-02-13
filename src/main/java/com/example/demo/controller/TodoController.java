package com.example.demo.controller;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TodoDTO;
import com.example.demo.model.TodoEntity;
import com.example.demo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("todo")
public class TodoController {

    @Autowired
    private TodoService service;

    @GetMapping("/test")
    public ResponseEntity<?> testTodo() {
        String str = service.testService();  // 테스트 서비스 사용

        List<String> list = new ArrayList<>();
        list.add(str);
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto) {
        try {
            String temporaryUserId = "temporary-user"; // temporary user id.

            TodoEntity entity = TodoDTO.toEntity(dto); // transfer to TodoEntity

            entity.setId(null); // initialize id to null
            entity.setUserId(temporaryUserId); // set temporary user id

            List<TodoEntity> entities = service.create(entity); // create TodoEntity
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList()); // transfer entity list to TodoDTO list

            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build(); // initialize ResponseDTO using transferred TodoDTO list

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();

            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping
    public ResponseEntity<?> retrieveTodoList() {
        String temporaryUserId = "temporary-user"; // temporary user id

        List<TodoEntity> entities = service.retrieve(temporaryUserId); // get TodoList using retrieve method
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList()); // transfer entity list to TodoDTO list using java stream

        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);
    }

    @PutMapping
    public ResponseEntity<?> updateTodo(@RequestBody TodoDTO dto) {
        String temporaryUserId = "temporary-user"; // temporary user id

        TodoEntity entity = TodoDTO.toEntity(dto);
        entity.setUserId(temporaryUserId); // initialize id

        List<TodoEntity> entities = service.update(entity); // update entity
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);
    }
}
