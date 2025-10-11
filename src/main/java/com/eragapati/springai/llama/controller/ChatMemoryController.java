package com.eragapati.springai.llama.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatMemoryController {

    private final ChatMemory chatMemory;

    @DeleteMapping("/chat/{id}/clearMemory")
    public ResponseEntity<String> clearChatMemory(@PathVariable String id) {
        log.info("clear chat memory for id {}", id);
        chatMemory.clear(id);
        return ResponseEntity.ok("Chat memory cleared for id: " + id);
    }
}
