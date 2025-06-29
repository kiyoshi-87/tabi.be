package com.kiyoshi87.tabi.controller;

import com.kiyoshi87.tabi.model.dto.request.ItineraryRequestDto;
import com.kiyoshi87.tabi.model.dto.response.ItineraryResponseDto;
import com.kiyoshi87.tabi.service.TabiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ollama")
@CrossOrigin("*")
@Slf4j
@RequiredArgsConstructor
public class TabiController {

    private final TabiService tabiService;

    @PostMapping("/generate")
    public ResponseEntity<ItineraryResponseDto> generate(@RequestBody ItineraryRequestDto request) {
        return ResponseEntity.ok(tabiService.generateItinerary(request));
    }
}
