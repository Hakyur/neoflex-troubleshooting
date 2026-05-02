package ru.rogotovsky.troubleshooting.dto;

public record UpdateTaskDto (
        String title,
        String description,
        String status,
        Integer priority
) {}
