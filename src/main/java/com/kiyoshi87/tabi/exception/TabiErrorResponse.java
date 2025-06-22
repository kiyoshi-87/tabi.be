package com.kiyoshi87.tabi.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TabiErrorResponse {
        private String error;
        private String message;
        private Integer status;
        private LocalDateTime timestamp;
}
