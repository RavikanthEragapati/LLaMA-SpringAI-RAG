package com.eragapati.springai.llama.utility;

import org.springframework.ai.chat.prompt.PromptTemplate;

import java.util.List;

public class AIPromptConstant {

    //SystemPrompt Constants
    public static final String CUSTOM_SYSTEM_PROMPT_TEMPLATE = """
            Assume role of an assistant     
            """;

    // QuestionAnswerAdvisor Constants
    public static final PromptTemplate CUSTOM_QA_ADVISOR_PROMPT_TEMPLATE = new PromptTemplate("""
            {query}
            
            Context information is below, surrounded by ---------------------
            
            ---------------------
            {question_answer_context}
            ---------------------
            
            Given the context and provided history information and not prior knowledge,
            reply to the user comment. 
            Do not respond saying provide the context information (surrounded by ---------------------) for me to work with
            If the answer is not in the context, inform the user that you can't answer the question. 
            
            """);

    // SafeGuardAdvisor Constants
    public static final List<String> FORBIDDEN_WORDS = List.of("duck", "damn");
    public static final String CUSTOM_FAILURE_RESPONSE = "I'm unable to respond to that due to sensitive content. Could we rephrase or discuss something else?";
}
