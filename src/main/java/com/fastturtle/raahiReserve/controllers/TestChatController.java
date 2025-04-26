package com.fastturtle.raahiReserve.controllers;

import com.fastturtle.raahiReserve.models.ChatRequest;
import org.springframework.ai.azure.openai.AzureOpenAiChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestChatController
{
    private final AzureOpenAiChatModel azureOpenAiChatModel;

    public TestChatController(AzureOpenAiChatModel azureOpenAiChatModel)
    {
        this.azureOpenAiChatModel = azureOpenAiChatModel;
    }

    @PostMapping("/chat")
    public String chat(@RequestBody ChatRequest request)
    {

        // For using gpt-35-turbo, DON'T use the same key as you used for gpt-4o
        // Use the 2nd key present in azure portal
        Prompt prompt = new Prompt(request.getPrompt());
        ChatResponse chatResponse = azureOpenAiChatModel.call(prompt);
        return chatResponse.getResults().get(0).getOutput().getText();
    }

    @GetMapping("/bookingSummary")
    public String bookingSummary()
    {
        String statsText = """
            Total Bookings: 312
            Cancellations: 21
            Top Routes:
            - Delhi to Jaipur (86 bookings)
            - Bangalore to Hyderabad (72 bookings)
            Preferred Times:
            - Travel Time: Morning
            - Booking Time: Evening
            Bus Type Preference:
            - AC Sleeper: 164
            - Non-AC Seater: 92
            Seat Preference:
            - Window: 65%
            - Aisle: 20%
            """;

        String promptString = "Please write a 2-3 line friendly summary of these bus booking stats. Please don't use markdown formatting:\n" + statsText;

        // For using gpt-35-turbo, DON'T use the same key as you used for gpt-4o
        // Use the 2nd key present in azure portal
        Prompt prompt = new Prompt(promptString);
        ChatResponse chatResponse = azureOpenAiChatModel.call(prompt);
        return chatResponse.getResults().get(0).getOutput().getText();
    }

}
