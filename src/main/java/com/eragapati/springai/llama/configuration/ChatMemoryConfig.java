package com.eragapati.springai.llama.configuration;

import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatMemoryConfig {

    @Bean
    ChatMemory buildChatMemory(ChatMemoryRepository repository) {
        return MessageWindowChatMemory.builder()
                .maxMessages(10)
                .build();
    }
}
