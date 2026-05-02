package ru.rogotovsky.troubleshooting.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.rogotovsky.troubleshooting.dto.CreateTaskDto;
import ru.rogotovsky.troubleshooting.dto.TaskDto;
import ru.rogotovsky.troubleshooting.dto.UpdateTaskDto;
import ru.rogotovsky.troubleshooting.entity.Task;
import ru.rogotovsky.troubleshooting.exception.TaskNotFoundException;
import ru.rogotovsky.troubleshooting.mapper.TaskMapper;
import ru.rogotovsky.troubleshooting.repository.TaskRepository;
import ru.rogotovsky.troubleshooting.util.ExceptionMessages;

import java.util.List;

import static ru.rogotovsky.troubleshooting.util.ExceptionMessages.TASK_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    public TaskDto createTask(CreateTaskDto requestDto) {
        log.trace(">> createTask");
        log.debug("CreateTaskDto: {}", requestDto);

        Task task = taskMapper.toTask(requestDto);
        task = taskRepository.save(task);

        log.info("Task successfully created with id={}", task.getId());
        log.trace( "<< createTask");
        return taskMapper.toTaskDto(task);
    }

    public List<TaskDto> getAllTasks() {
        log.trace(">> getAllTasks");

        List<Task> tasks = taskRepository.findAll();

        log.debug("Found {} tasks", tasks.size());

        log.trace("<< getAllTasks");
        return tasks.stream()
                .map(taskMapper::toTaskDto)
                .toList();
    }

    public TaskDto getTaskById(Long id) {
        log.trace(">> getTaskById");

        Task task = getById(id);

        log.debug("Task found: {}", task);

        log.trace("<< getTaskById");
        return taskMapper.toTaskDto(task);
    }

    public TaskDto updateTask(Long id, UpdateTaskDto requestDto) {
        log.trace(">> updateTask");
        log.info("Updating task with id={}", id);
        log.debug("Update payload: {}", requestDto);

        Task task = getById(id);

        task.setTitle(requestDto.title());
        task.setDescription(requestDto.description());
        task.setStatus(requestDto.status());
        task.setPriority(requestDto.priority());

        task = taskRepository.save(task);

        log.debug("Updated task state: {}", task);
        log.info("Task updated successfully with id={}", id);
        log.trace("<< updateTask");
        return taskMapper.toTaskDto(task);
    }

    public void deleteTask(Long id) {
        log.trace(">> deleteTask");

        Task task = getById(id);
        taskRepository.delete(task);

        log.info("Task deleted with id={}", id);
        log.trace("<< deleteTask");
    }

    private Task getById(Long id) {
        log.trace(">> getById");

        Task task = taskRepository.findById(id).orElseThrow(
                () -> {
                    log.error("Task not found with id={}", id);
                    return new TaskNotFoundException(TASK_NOT_FOUND.formatted(id));
                }
        );
        log.trace("<< getById");
        return task;
    }

    public Long simulateLoad() {
        log.warn("Starting heavy CPU load simulation");

        long result = 0;

        for (long i = 0; i < 300_000_000L; i++) {
            result += i % 7;
        }

        log.warn("Finished load simulation");
        return result;
    }
}
