package com.eragapati.springai.llama.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.eragapati.springai.llama.utility.AIPromptConstant.*;

@Service
@RequiredArgsConstructor
public class LlamaPromptService {

    private final ChatClient chatClient;
    private final VectorStore simpleVectorStore;

    public String callAIModel(String userPrompt) {
        return this.chatClient.prompt().user(userPrompt).call().content();
    }

    public String callAIModelUsingRAG(String userPrompt, String conversationId) {

        var userMessage = new UserMessage(userPrompt);
        var systemMessage = new SystemPromptTemplate(CUSTOM_SYSTEM_PROMPT_TEMPLATE).createMessage();
        var prompt = Prompt.builder().messages(List.of(userMessage, systemMessage)).build();

        var safeGuardAdvisor = new SafeGuardAdvisor(FORBIDDEN_WORDS, CUSTOM_FAILURE_RESPONSE, 0);
        var questionAnswerAdvisor = QuestionAnswerAdvisor.builder(simpleVectorStore)
                .promptTemplate(CUSTOM_QA_ADVISOR_PROMPT_TEMPLATE)
                .searchRequest(SearchRequest.builder().similarityThreshold(0.4d).topK(4).build())
                .build();

        return chatClient.prompt()
                .user(userMessage.getText())
                .system(systemMessage.getText())
                .advisors(safeGuardAdvisor)
                .advisors(questionAnswerAdvisor)
                .advisors(a -> a.param(ChatMemory.CONVERSATION_ID, conversationId))
                .advisors(new SimpleLoggerAdvisor())
                //.Tools
                //.MCP tools
                .call().content();
    }

    private String vectorSimilaritySearch(String userPrompt) {
        var checkRequest = SearchRequest.builder()
                .query(userPrompt)
                .topK(1)
                .build();
        var similarDocumentsList = this.simpleVectorStore.similaritySearch(checkRequest);

        var documents = similarDocumentsList.stream()
                .map(Document::getText)
                .collect(Collectors.joining(System.lineSeparator()));
        return documents;
    }

}
