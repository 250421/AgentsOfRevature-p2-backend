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
        // we don't control callGemini directly anymore, so we must inject it via reflection or setter,
        // but for simplicity let's subclass
        GeminiService svc = new GeminiService() {
            @Override
            public String getValidResponse(String p, Function<String, Boolean> v) {
                // delegate to original logic but override callGemini
                this.callGemini = pr -> "valid";
                return super.getValidResponse(p, v);
            }
        };

        String result = svc.getValidResponse(prompt, validator);
        assertEquals("valid", result);
    }

    @Test
    void testGetValidResponse_ValidOnSecondTry() {
        String prompt = "prompt";
        AtomicInteger callCount = new AtomicInteger();
        Function<String, Boolean> validator = resp -> resp.equals("valid");
        GeminiService svc = new GeminiService() {
            @Override
            public String getValidResponse(String p, Function<String, Boolean> v) {
                this.callGemini = pr ->
                        callCount.getAndIncrement() == 0 ? "invalid" : "valid";
                return super.getValidResponse(p, v);
            }
        };

        String result = svc.getValidResponse(prompt, validator);
        assertEquals("valid", result);
    }

    @Test
    void testGetValidResponse_InvalidBothTries() {
        String prompt = "prompt";
        Function<String, Boolean> validator = resp -> false;
        GeminiService svc = new GeminiService() {
            @Override
            public String getValidResponse(String p, Function<String, Boolean> v) {
                this.callGemini = pr -> "still invalid";
                return super.getValidResponse(p, v);
            }
        };

        IllegalStateException ex = assertThrows(
                IllegalStateException.class,
                () -> svc.getValidResponse(prompt, validator)
        );
        assertTrue(ex.getMessage().contains("Gemini did not return a valid format after"));
        assertTrue(ex.getMessage().contains("still invalid"));
    }

    @Test
    void testGenerate_invokesCallGeminiDirectly() {
        String prompt = "closing";
        GeminiService svc = new GeminiService() {
            @Override
            public String generate(String p) {
                return "final:" + p;
            }
        };
        assertEquals("final:closing", svc.generate(prompt));
    }
}
