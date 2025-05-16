package com.anonymousibex.Agents.of.Revature.service;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;

class GeminiServiceTest {

    private final GeminiService geminiService = new GeminiService();

    @Test
    void testGetValidResponse_ValidOnFirstTry() {
        String prompt = "prompt";
        Function<String, Boolean> validator = resp -> resp.equals("valid");
        Function<String, String> callGemini = p -> "valid";

        String result = geminiService.getValidResponse(prompt, validator, callGemini);
        assertEquals("valid", result);
    }

    @Test
    void testGetValidResponse_ValidOnSecondTry() {
        String prompt = "prompt";
        AtomicInteger callCount = new AtomicInteger(0);
        Function<String, Boolean> validator = resp -> resp.equals("valid");
        Function<String, String> callGemini = p -> callCount.getAndIncrement() == 0 ? "invalid" : "valid";

        String result = geminiService.getValidResponse(prompt, validator, callGemini);
        assertEquals("valid", result);
    }

    @Test
    void testGetValidResponse_InvalidBothTries() {
        String prompt = "prompt";
        Function<String, Boolean> validator = resp -> false;
        Function<String, String> callGemini = p -> "invalid";

        IllegalStateException ex = assertThrows(IllegalStateException.class,
                () -> geminiService.getValidResponse(prompt, validator, callGemini));
        assertTrue(ex.getMessage().contains("Gemini did not return a valid format after"));
        assertTrue(ex.getMessage().contains("invalid"));
    }
}
