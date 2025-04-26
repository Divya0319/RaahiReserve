package com.fastturtle.raahiReserve.controllers;

import com.fastturtle.raahiReserve.models.ChatRequest;
import org.springframework.ai.azure.openai.AzureOpenAiChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
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

}
