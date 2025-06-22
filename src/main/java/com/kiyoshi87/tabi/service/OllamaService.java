package com.kiyoshi87.tabi.service;

import com.kiyoshi87.tabi.exception.TabiExceptionSupplier;
import com.kiyoshi87.tabi.model.dto.request.ItineraryRequestDto;
import com.kiyoshi87.tabi.model.dto.response.ItineraryResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import static com.kiyoshi87.tabi.util.TabiPromptUtil.GENERATION_PROMPT;
import static com.kiyoshi87.tabi.util.TabiValidationUtil.validateCities;
import static com.kiyoshi87.tabi.util.TabiValidationUtil.validateRequestedDate;

@Service
@Slf4j
public class OllamaService {

    private final ChatClient chatClient;

    public OllamaService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public ItineraryResponseDto generateItinerary(ItineraryRequestDto request) {
        validateRequestedDate(request.startDateTime(), request.endDateTime());
        validateCities(request.cities());

        String prompt = buildPrompt(request);
        log.info("Generating full itinerary for cities: {}", request.cities());

        try {
            String response = chatClient.prompt()
                    .user(prompt)
                    .call()
                    .content();

            return ItineraryResponseDto.builder()
                    .data(response)
                    .build();
        } catch (Exception ex) {
            log.error("Itinerary generation failed: {}", ex.getMessage(), ex);
            throw TabiExceptionSupplier.internalError("Something went wrong with LLM model").get();
        }
    }

    private String buildPrompt(ItineraryRequestDto request) {
        return String.format(GENERATION_PROMPT,
                request.startDateTime(),
                request.endDateTime(),
                String.join(", ", request.cities())
        );
    }
}