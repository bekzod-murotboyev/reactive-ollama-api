package uz.keepsolution.reactiveollamaapi.configuration.ai.openai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import uz.keepsolution.reactiveollamaapi.configuration.ai.tool.datetime.DateTimeTool;
import uz.keepsolution.reactiveollamaapi.utils.AIUtils;

@Configuration
public class OpenaiConfiguration {

    public static final String OPENAI_CHAT_CLIENT = "openaiChatClient";

    @Primary
    @Bean(OPENAI_CHAT_CLIENT)
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

}
