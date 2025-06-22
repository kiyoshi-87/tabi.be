package com.kiyoshi87.tabi.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TabiPromptUtil {

    public static final String GENERATION_PROMPT = """
            Create a detailed day-wise travel itinerary from %s to %s for the following cities: %s.
            - Assume you are already in the starting city.
            - Include city-to-city travel if multiple cities are provided, activities, meals, rest, and transportation.
            - Do not suggest moving to a different city as part of the itinerary, suggest activities only in the given city.
            - Start with the itinerary directly no need to add any placeholder sentence before.
            - Format using markdown:
              * Use `### Day X - Date` for day headers
              * Use `-` for bullet points
              * Use emojis as well for better visual representation.
              * Use bullet points based on time
              * Add line breaks after each bullet point
              * Use `**bold**` for time or highlights
            - Do not use code blocks or HTML.
            - Keep it readable and structured clearly.
            """;

    /**
     * @deprecated This prompt version is deprecated and will be removed in the future.
     * Use the new format for the buildPrompt() method instead.
     */
    @Deprecated(forRemoval = false)
    public static final String GENERATION_PROMPT_V2 = """
            Plan a day-wise travel itinerary from %s to %s for these cities: %s.
            Include transport, meals, attractions, and rest.
            Format using markdown:
            ### Day X - Date
            - **Time**: Activity
            Do not use HTML or code blocks.
            """;
}
