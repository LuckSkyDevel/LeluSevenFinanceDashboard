package com.finance.leluseven.shared.infrastructure.payload;

import org.springframework.http.ResponseEntity;

import java.net.URI;

public record ApiResponse<T>(String message, ResponseEntity<T> data) {
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>("Sucesso", ResponseEntity.ok().body(data));
    }

    public static <T> ApiResponse<T> created(T data, String message, URI location) {
        return new ApiResponse<>(message, ResponseEntity.created(location).body(data));
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(message, ResponseEntity.badRequest().body(null));
    }
}
