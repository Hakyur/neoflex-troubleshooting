package ru.rogotovsky.troubleshooting.mapper;

import org.mapstruct.Mapper;
import ru.rogotovsky.troubleshooting.dto.CreateTaskDto;
import ru.rogotovsky.troubleshooting.dto.TaskDto;
import ru.rogotovsky.troubleshooting.entity.Task;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    Task toTask(CreateTaskDto dto);
    TaskDto toTaskDto(Task task);
}
