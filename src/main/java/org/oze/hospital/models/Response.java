package org.oze.hospital.models;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Response {
    private boolean hasError;
    private String message;
    private Object data;
    public Response(boolean hasError, String message, Object data) {
        this.hasError = hasError;
        this.message = message;
        this.data = data;
    }
}
