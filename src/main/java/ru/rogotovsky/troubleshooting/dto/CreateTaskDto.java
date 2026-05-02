package ru.rogotovsky.troubleshooting.dto;

public record CreateTaskDto (
        String title,
        String description,
        String status,
        Integer priority
) {}
