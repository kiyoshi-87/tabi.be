package com.kiyoshi87.tabi.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.util.List;

import static com.kiyoshi87.tabi.exception.TabiExceptionSupplier.validationError;

@UtilityClass
public class TabiValidationUtil {

    public static void validateRequestedDate(LocalDateTime startDateTime,
            LocalDateTime endDateTime) {
        if (startDateTime == null || endDateTime == null) {
            throw validationError("Start date and end date must not be null").get();
        }

        if (startDateTime.isBefore(LocalDateTime.now())) {
            throw validationError("Start date must not be in the past").get();
        }

        if (endDateTime.isBefore(startDateTime)) {
            throw validationError("End date must be after start date").get();
        }
    }

    public static void validateCities(List<String> cities) {
        if (cities == null || cities.isEmpty()) {
            throw validationError("Cities list must not be null or empty").get();
        }

        for (String city : cities) {
            if (city == null || city.trim().isEmpty()) {
                throw validationError("Cities must not contain null, empty, or blank strings").get();
            }
        }
    }
}
