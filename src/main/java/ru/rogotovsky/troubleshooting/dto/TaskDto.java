package ru.rogotovsky.troubleshooting.dto;

import java.time.LocalDateTime;

public record TaskDto (
        Long id,
        String title,
        String description,
        String status,
        Integer priority,
        LocalDateTime createdAt
) {}
