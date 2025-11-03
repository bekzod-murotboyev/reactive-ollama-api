package uz.keepsolution.reactiveollamaapi.configuration.ai.ollama;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uz.keepsolution.reactiveollamaapi.configuration.ai.tool.datetime.DateTimeTool;
import uz.keepsolution.reactiveollamaapi.utils.AIUtils;

@Configuration(proxyBeanMethods = false)
public class OllamaChatClientConfiguration {


    public static final String STREAMING_CHAT_CLIENT = "streamChatClient";
    public static final String MATH_STRUCTURED_CHAT_CLIENT = "mathStructuredChatClient";
    public static final String BLOCKING_CHAT_CLIENT = "blockingChatClient";
    public static final String VISION_CHAT_CLIENT = "visionChatClient";


    @Bean(STREAMING_CHAT_CLIENT)
    public ChatClient streamChatClient(
            @Qualifier(OllamaChatModelConfiguration.DEFAULT_CHAT_MODEL) OllamaChatModel chatModel,
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

    @Bean(BLOCKING_CHAT_CLIENT)
    public ChatClient blockingChatClient(
            @Qualifier(OllamaChatModelConfiguration.DEFAULT_CHAT_MODEL) OllamaChatModel chatModel,
            ChatMemory chatMemory
    ) {
        return ChatClient
                .builder(chatModel)
                .defaultSystem(AIUtils.CHAT_CLIENT_PROMPT_RULE)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }

    @Bean(MATH_STRUCTURED_CHAT_CLIENT)
    public ChatClient mathStructuredChatClient(
            @Qualifier(OllamaChatModelConfiguration.MATH_STRUCTURED_CHAT_MODEL) OllamaChatModel chatModel,
            ChatMemory chatMemory,
            DateTimeTool dateTimeTool
    ) {
        return ChatClient
                .builder(chatModel)
                .defaultSystem(AIUtils.MATH_STRUCTURED_PROMPT_RULE)
                .defaultTools(dateTimeTool)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatMemory).build())
                .build();
    }


    @Bean(VISION_CHAT_CLIENT)
    public ChatClient visionChatClient(
            @Qualifier(OllamaChatModelConfiguration.VISION_CHAT_MODEL) OllamaChatModel chatModel) {
        return ChatClient
                .builder(chatModel)
                .defaultSystem(AIUtils.VISION_CLIENT_PROMPT_RULE)
                .build();
    }


}
