package com.medicinecheck.service;

import com.medicinecheck.model.Medicine;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AIService {

    private final MedicineService medicineService;
    private final RestTemplate restTemplate;

    public AIService(MedicineService medicineService) {
        this.medicineService = medicineService;
        this.restTemplate = new RestTemplate();
    }

    public List<Medicine> aiSearch(String query) {
        try {
            // Prepare the AI request
            String prompt = buildPrompt(query);

            // Call Claude API
            String aiResponse = callClaudeAPI(prompt);

            // Extract medicine names from AI response
            List<String> suggestedNames = extractMedicineNames(aiResponse);

            // Filter medicines from database
            return filterMedicinesByNames(suggestedNames);

        } catch (Exception e) {
            // Fallback to basic search if AI fails
            System.out.println("AI search failed, using fallback: " + e.getMessage());
            return medicineService.searchMedicines(query);
        }
    }

    private String buildPrompt(String query) {
        List<Medicine> allMeds = medicineService.getAllMedicines();
        StringBuilder prompt = new StringBuilder();

        prompt.append("You are a medical assistant helping find medicines. Given the search query: \"")
                .append(query)
                .append("\"\n\nAvailable medicines in database:\n");

        for (Medicine med : allMeds) {
            prompt.append("- ").append(med.getName())
                    .append(" (").append(med.getGeneric()).append(") - ")
                    .append(med.getCategory())
                    .append(" - Used for: ").append(String.join(", ", med.getKeywords()))
                    .append("\n");
        }

        prompt.append("\nTask: Analyze the search query and return ONLY a JSON array of medicine names that match, ");
        prompt.append("even if there are typos or natural language descriptions. Consider:\n");
        prompt.append("1. Exact matches\n");
        prompt.append("2. Misspellings (e.g., \"asprin\" -> \"Aspirin\")\n");
        prompt.append("3. Natural language (e.g., \"painkiller\" -> [\"Aspirin\", \"Ibuprofen\", \"Acetaminophen\"])\n");
        prompt.append("4. Generic names\n");
        prompt.append("5. Common use cases\n\n");
        prompt.append("Return ONLY valid JSON array format like: [\"Medicine1\", \"Medicine2\"]\n");
        prompt.append("If no matches, return: []");

        return prompt.toString();
    }

    private String callClaudeAPI(String prompt) {
        String apiUrl = "https://api.anthropic.com/v1/messages";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // Note: API key is handled by the proxy, no need to set it here

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "claude-sonnet-4-20250514");
        requestBody.put("max_tokens", 1000);

        List<Map<String, String>> messages = new ArrayList<>();
        Map<String, String> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", prompt);
        messages.add(message);

        requestBody.put("messages", messages);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);

        @SuppressWarnings("unchecked")
        Map<String, Object> response = restTemplate.postForObject(apiUrl, request, Map.class);

        if (response != null && response.containsKey("content")) {
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> content = (List<Map<String, Object>>) response.get("content");
            if (!content.isEmpty()) {
                return (String) content.get(0).get("text");
            }
        }

        return "[]";
    }

    private List<String> extractMedicineNames(String aiResponse) {
        List<String> names = new ArrayList<>();

        // Extract JSON array from response
        Pattern pattern = Pattern.compile("\\[([^\\]]+)\\]");
        Matcher matcher = pattern.matcher(aiResponse);

        if (matcher.find()) {
            String jsonContent = matcher.group(1);
            // Simple parsing: split by comma and clean quotes
            String[] parts = jsonContent.split(",");
            for (String part : parts) {
                String cleaned = part.trim().replaceAll("\"", "");
                if (!cleaned.isEmpty()) {
                    names.add(cleaned);
                }
            }
        }

        return names;
    }

    private List<Medicine> filterMedicinesByNames(List<String> names) {
        List<Medicine> allMeds = medicineService.getAllMedicines();
        List<Medicine> filtered = new ArrayList<>();

        for (Medicine med : allMeds) {
            if (names.contains(med.getName())) {
                filtered.add(med);
            }
        }

        return filtered;
    }
}