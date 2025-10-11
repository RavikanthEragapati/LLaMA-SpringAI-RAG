package com.eragapati.springai.llama.controller;

import com.eragapati.springai.llama.service.LlamaPromptService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final LlamaPromptService llamaPromptService;

    /**
     * This endpoint doesnot uses RAG nor the Prompt was built to add context so the outcome will not be streamlined.
     *
     * @param userPrompt - the original token/question
     * @param id         - Will be used in future to track chat history for individual chat id
     * @return - Map that contains response
     */
    @GetMapping("/chat/{id}/ask")
    public Map<String, String> ask(
            @RequestParam(value = "userPrompt", defaultValue = "Tell me a joke") String userPrompt,
            @PathVariable String id) {
        return Map.of("response", llamaPromptService.callAIModel(userPrompt));
    }

    /**
     * This endpoint uses RAG and also a custom Prompt is built to add context and attach documents retrieved from VectorStore that have close similarity to the question.
     *
     * @param userPrompt - the original token/question
     * @param id         - Will be used in future to track chat history for individual chat id
     * @return - Map that contains response
     */
    @GetMapping("/chat/{id}/askUsingRAG")
    public Map<String, String> askUsingRAG(
            @RequestParam(value = "userPrompt", defaultValue = "Tell me a joke") String userPrompt,
            @PathVariable String id) {
        return Map.of("response", llamaPromptService.callAIModelUsingRAG(userPrompt, id));
    }
}
