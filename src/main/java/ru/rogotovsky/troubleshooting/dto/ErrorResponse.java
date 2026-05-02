package ru.rogotovsky.troubleshooting.dto;

import java.time.LocalDateTime;

public record ErrorResponse (
        String message,
        LocalDateTime timestamp
) {}
