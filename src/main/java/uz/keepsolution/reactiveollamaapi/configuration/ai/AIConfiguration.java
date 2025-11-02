package uz.keepsolution.reactiveollamaapi.configuration.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import uz.keepsolution.reactiveollamaapi.configuration.ai.tool.datetime.DateTimeTool;
import uz.keepsolution.reactiveollamaapi.utils.AIUtils;

@Configuration
public class AIConfiguration {

    public static final String OPEN_AI_CHAT_CLIENT = "openAiChatClient";
    public static final String OLLAMA_CHAT_CLIENT = "ollamaChatClient";

    @Primary
    @Bean(OPEN_AI_CHAT_CLIENT)
    public ChatClient openAiChatClient(
            OpenAiChatModel chatModel,
            ChatMemory chatMemory,
            DateTimeTool dateTimeTool
    ) {
        return ChatClient
                .builder(chatModel)
                .defaultSystem(AIUtils.CHAT_CLIENT_PROMPT_RULE)
                .defaultTools(dateTimeTool)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }

    @Bean(OLLAMA_CHAT_CLIENT)
    public ChatClient ollamaChatClient(
            OllamaChatModel chatModel,
            ChatMemory chatMemory,
            DateTimeTool dateTimeTool
    ) {
        return ChatClient
                .builder(chatModel)
                .defaultSystem(AIUtils.CHAT_CLIENT_PROMPT_RULE)
                .defaultTools(dateTimeTool)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }


}
