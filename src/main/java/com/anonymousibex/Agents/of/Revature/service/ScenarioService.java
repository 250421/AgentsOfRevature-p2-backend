package com.anonymousibex.Agents.of.Revature.service;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScenarioService {

    public String start(String request){
        Client client = new Client();
        GenerateContentResponse response =
                client.models.generateContent("gemini-2.0-flash-001", request, null);
        return response.text();
    }

}
