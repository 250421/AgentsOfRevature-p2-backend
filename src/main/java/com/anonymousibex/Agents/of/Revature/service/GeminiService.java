package com.anonymousibex.Agents.of.Revature.service;

import com.google.genai.Client;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class GeminiService {

    private static final int MAX_RETRIES = 2;

    @Value("${GOOGLE_API_KEY}")
    private String googleApiKey;

    public Function<String, String> callGemini;

    @PostConstruct
    public void init() {
        this.callGemini = prompt ->
                Client.builder()
                        .apiKey(googleApiKey)
                        .build()
                        .models
                        .generateContent("gemini-2.0-flash-001", prompt, null)
                        .text();
    }


    public String getValidResponse(
            String prompt,
            Function<String, Boolean> validator
    ) {
        String last = null;
        for (int attempt = 0; attempt < MAX_RETRIES; attempt++) {
            String resp = callGemini.apply(prompt);
            if (validator.apply(resp)) {
                return resp;
            }
            last = resp;
            prompt = "Reminder: use '>' EXACTLY as delimiter, no extra text.\n\n" + prompt;
        }
        throw new IllegalStateException(
                "Gemini did not return a valid format after " + MAX_RETRIES +
                        " attempts. Last response:\n" + last
        );
    }


    public String generate(String prompt) {
        return callGemini.apply(prompt);
    }
}

