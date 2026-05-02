package ru.rogotovsky.troubleshooting.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rogotovsky.troubleshooting.dto.CreateTaskDto;
import ru.rogotovsky.troubleshooting.dto.TaskDto;
import ru.rogotovsky.troubleshooting.dto.UpdateTaskDto;
import ru.rogotovsky.troubleshooting.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@Slf4j
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskDto> createTask(@RequestBody CreateTaskDto requestDto) {
        log.info("POST /tasks - creating new task");
        log.debug("Request body: {}", requestDto);

        TaskDto result = taskService.createTask(requestDto);

        log.info("POST /tasks completed");
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(result);
    }

    @GetMapping
    public ResponseEntity<List<TaskDto>> getAllTasks() {
        log.info("HTTP GET /tasks - fetching all tasks");

        List<TaskDto> result = taskService.getAllTasks();

        log.debug("Returned {} tasks", result.size());
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getTaskById(@PathVariable Long id) {
        log.info("GET /tasks/{} - fetching task", id);

        TaskDto result = taskService.getTaskById(id);

        log.info("GET /tasks/{} completed", id);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDto> updateTask(@PathVariable Long id, @RequestBody UpdateTaskDto requestDto) {
        log.info("HTTP PUT /tasks/{} - updating task", id);
        log.debug("Update payload: {}", requestDto);

        TaskDto result = taskService.updateTask(id, requestDto);

        log.info("Task updated with id={}", id);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        log.warn("HTTP DELETE /tasks/{} - deleting task", id);

        taskService.deleteTask(id);

        log.info("Task deleted with id={}", id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/load")
    public ResponseEntity<Long> load() {
        return ResponseEntity.ok(taskService.simulateLoad());
    }

}
