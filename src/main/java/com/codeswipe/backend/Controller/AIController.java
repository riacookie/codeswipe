package com.codeswipe.backend.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AIController {

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    private final String GEMINI_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash-lite:generateContent?key=";

    @PostMapping("/genProject")
    public Map<String, String> generateProject(@RequestParam String prompt) {
        RestTemplate restTemplate = new RestTemplate();

        // Construct the prompt for Gemini
        String systemInstruction = "You are a creative project generator. Given a user idea, generate a coding project details. " +
                "Return result ONLY as valid JSON without Markdown formatting. " +
                "The JSON must have keys: 'title', 'description', 'difficultyLevel' (choose from Beginner, Intermediate, Advanced), 'skillName' (comma separated list of technologies).";

        String fullPrompt = systemInstruction + " User Idea: " + prompt;

        // Request Body
        Map<String, Object> requestBody = new HashMap<>();
        Map<String, Object> content = new HashMap<>();
        Map<String, String> parts = new HashMap<>();
        parts.put("text", fullPrompt);
        content.put("parts", new Object[]{parts});
        requestBody.put("contents", new Object[]{content});

        // Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    GEMINI_URL + geminiApiKey,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            // Parse Response
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            String generatedText = root.path("candidates").get(0).path("content").path("parts").get(0).path("text").asText();

            // Clean up code block markers if present (Gemini might wrap in ```json ... ```)
            generatedText = generatedText.replaceAll("```json", "").replaceAll("```", "").trim();

            // Parse the inner JSON
            Map<String, String> result = mapper.readValue(generatedText, Map.class);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            Map<String, String> error = new HashMap<>();
            error.put("error", "Failed to generate project: " + e.getMessage());
            return error;
        }
    }
}

