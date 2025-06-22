package com.kiyoshi87.tabi.model.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record ItineraryRequestDto (
        @NotNull
        @NotEmpty
        List<String> cities,

        @NotNull
        LocalDateTime startDateTime,

        @NotNull
        LocalDateTime endDateTime
) {
}
