package com.finance.leluseven.shared.infrastructure.payload;

public record ApiResponse<T>(String message, T data) {
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>("Sucesso", data);
    }

    public static <T> ApiResponse<T> created(T data, String message) {
        return new ApiResponse<>(message, data);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(message, null);
    }
}
