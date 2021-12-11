package com.example.endofgame.dto;

import java.time.LocalDateTime;

public record ErrorResponse(LocalDateTime timestamp, String genericMessage,
                            String detailMessage, int responseStatus, String path) {
}
