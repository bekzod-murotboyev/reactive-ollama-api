package uz.keepsolution.reactiveollamaapi.configuration;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uz.keepsolution.reactiveollamaapi.utils.AIUtils;

@Configuration
public class AIConfiguration {

    @Bean
    public ChatClient chatClient(OpenAiChatModel chatModel) {
        return ChatClient
                .builder(chatModel)
                .defaultSystem(AIUtils.CHAT_CLIENT_PROMPT_RULE)
                .build();
    }
}
