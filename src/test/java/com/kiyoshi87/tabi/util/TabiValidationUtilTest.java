package com.kiyoshi87.tabi.util;

import com.kiyoshi87.tabi.exception.TabiApiException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.time.LocalDateTime;
import java.util.List;

import static com.kiyoshi87.tabi.exception.TabiExceptionType.VALIDATION_ERROR;
import static org.junit.jupiter.api.Assertions.*;

class TabiValidationUtilTest {

    @Test
    void whenValidDates_thenNoException() {
        // Given
        LocalDateTime startDateTime = LocalDateTime.now().plusMinutes(1);
        LocalDateTime endDateTime = startDateTime.plusDays(5);

        assertDoesNotThrow(() -> TabiValidationUtil.validateRequestedDate(startDateTime, endDateTime));
    }

    @Test
    void whenEndDateBeforeStartDate_thenThrowsException() {
        // Given
        LocalDateTime startDateTime = LocalDateTime.now();
        LocalDateTime endDateTime = startDateTime.minusDays(1);

        // When & Then
        TabiApiException exception = assertThrows(TabiApiException.class,
                () -> TabiValidationUtil.validateRequestedDate(startDateTime, endDateTime));

        assertEquals(VALIDATION_ERROR, exception.getType());
    }

    @Test
    void whenStartDateInPast_thenThrowsException() {
        // Given
        LocalDateTime startDateTime = LocalDateTime.now().minusDays(1);
        LocalDateTime endDateTime = LocalDateTime.now().plusDays(5);

        // When & Then
        TabiApiException exception = assertThrows(TabiApiException.class,
                () -> TabiValidationUtil.validateRequestedDate(startDateTime, endDateTime));

        assertEquals(VALIDATION_ERROR, exception.getType());
    }

    @Test
    void whenValidCities_thenNoException() {
        // Given
        List<String> cities = List.of("Tokyo", "Kyoto", "Osaka");

        // When & Then
        assertDoesNotThrow(() -> TabiValidationUtil.validateCities(cities));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void whenCitiesIsNullOrEmpty_thenThrowsException(List<String> cities) {
        // When & Then
        TabiApiException exception = assertThrows(TabiApiException.class,
                () -> TabiValidationUtil.validateCities(cities));

        assertEquals(VALIDATION_ERROR, exception.getType());
    }

    @Test
    void whenCitiesContainsEmptyString_thenThrowsException() {
        // Given
        List<String> cities = List.of("Tokyo", "", "Osaka");

        // When & Then
        TabiApiException exception = assertThrows(TabiApiException.class,
                () -> TabiValidationUtil.validateCities(cities));

        assertEquals(VALIDATION_ERROR, exception.getType());
        assertTrue(exception.getMessage().contains("empty"));
    }

    @Test
    void whenCitiesContainsBlankString_thenThrowsException() {
        // Given
        List<String> cities = List.of("Tokyo", "   ", "Osaka");

        // When & Then
        TabiApiException exception = assertThrows(TabiApiException.class,
                () -> TabiValidationUtil.validateCities(cities));

        assertEquals(VALIDATION_ERROR, exception.getType());
        assertTrue(exception.getMessage().contains("blank"));
    }
}