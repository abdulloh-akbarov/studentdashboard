package com.zero.studentdashboard.model;

import com.zero.studentdashboard.model.enums.Message;

/**
 * The `Response` class is a simple data structure that represents a response to an API request.
 * It contains a status code, a message, and optional data payload.
 *
 * @author Abdulloh Akbarov
 */

public record Response(
        Integer status,
        String message,
        Object data) {
    public Response(Message message, Object data) {
        this(message.getStatus(), message.getMessage(), data);
    }

    public Response(Message message) {
        this(message.getStatus(), message.getMessage(), null);
    }

    public Response(Integer status, String message) {
        this(status, message, null);
    }
}
