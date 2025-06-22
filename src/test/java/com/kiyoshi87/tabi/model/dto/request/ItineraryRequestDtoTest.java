package com.kiyoshi87.tabi.model.dto.request;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ItineraryRequestDtoTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenValidRequest_thenNoViolations() {
        // Given
        ItineraryRequestDto request = new ItineraryRequestDto(
                List.of("Tokyo", "Kyoto"),
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(5));

        // When
        var violations = validator.validate(request);

        // Then
        assertTrue(violations.isEmpty(), "Should not have any validation violations");
    }

    @Test
    void whenCitiesIsNullOrEmpty_thenViolation() {
        // Given
        ItineraryRequestDto request = new ItineraryRequestDto(
                List.of(),
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(5));

        // When
        var violations = validator.validate(request);

        // Then
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertEquals(1, violations.size(), "Should have exactly one violation");
        assertEquals("cities", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    void whenStartDateTimeIsNull_thenViolation() {
        // Given
        ItineraryRequestDto request = new ItineraryRequestDto(
                List.of("Tokyo", "Kyoto"),
                null,
                LocalDateTime.now().plusDays(5));

        // When
        var violations = validator.validate(request);

        // Then
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertEquals(1, violations.size(), "Should have exactly one violation");
        assertEquals("startDateTime", violations.iterator().next().getPropertyPath().toString());
    }

    @Test
    void whenEndDateTimeIsNull_thenViolation() {
        // Given
        ItineraryRequestDto request = new ItineraryRequestDto(
                List.of("Tokyo", "Kyoto"),
                LocalDateTime.now(),
                null);

        // When
        var violations = validator.validate(request);

        // Then
        assertFalse(violations.isEmpty(), "Should have validation violations");
        assertEquals(1, violations.size(), "Should have exactly one violation");
        assertEquals("endDateTime", violations.iterator().next().getPropertyPath().toString());
    }
}